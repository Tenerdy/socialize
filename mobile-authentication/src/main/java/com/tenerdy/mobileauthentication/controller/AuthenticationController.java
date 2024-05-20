package com.tenerdy.mobileauthentication.controller;

import com.alibaba.fastjson.JSONArray;
import com.tenerdy.mobileauthentication.bean.CategoryTag;
import com.tenerdy.mobileauthentication.mapper.UserTagsMapper;
import com.tenerdy.mobileauthentication.service.LoginService;
import com.tenerdy.mobileauthentication.service.UserInfoService;
import com.tenerdy.mobiledomain.entity.UserInfo;
import com.tenerdy.mobiledomain.entity.UserTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tenerdy.mobiledomain.bean.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class AuthenticationController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserTagsMapper userTagsMapper;

    private static final String UPLOAD_FOLDER = "D:/uploads/";

    @PostMapping("/login")
    public JsonResult login(String phone, String password) {
        String token = "";
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (loginService.login(phone, password)) {
            token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token, phone, Duration.ofSeconds(7200));
            return new JsonResult<>(HttpStatus.OK, true, "登录成功", token, userInfo);
        }
        return new JsonResult<>(HttpStatus.OK, false, "账户不存在或密码错误", token, null);
    }

    //上传头像并注册
    @PostMapping("/register")
    public JsonResult register(@RequestParam("file") MultipartFile file,  @RequestParam("phone")String phone, @RequestParam("password")String password, @RequestParam("nickname") String nickname, @RequestParam("newFilename") String newFilename,@RequestParam("tags")String tags,@RequestParam("sex")Integer sex,@RequestParam("birthday")String birthday) throws ParseException {
        String token = "";
        try {
            // 如果目录不存在则创建上传目录
            File uploadFolder = new File(UPLOAD_FOLDER);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            //拼接文件名
            byte[] bytes = file.getBytes();
            // 将文件保存到上传目录中
            Path path = Paths.get(UPLOAD_FOLDER + newFilename);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdayDate = formatter.parse(birthday);
       UserInfo userInfo = UserInfo.builder()
                .phone(phone)
                .password(password)
                .nickname(nickname)
                .picture(newFilename)
                .birthday(birthdayDate)
                .sex(sex)
                .registerTime(new Date())
               .watch(0)
                .build();
        userInfoService.register(userInfo);
        userInfo = userInfoService.getByPhone(phone);

        // 插入用户标签
        List<Integer> tagList = JSONArray.parseArray(tags, Integer.class);
        for (Integer id : tagList) {
            userTagsMapper.insert( UserTags.builder()
                    .userId(userInfo.getUserId())
                    .tagId(id)
                    .build());
        }

        return new JsonResult<>(HttpStatus.OK, true, "注册成功", token, userInfo);

    }
    //检查手机号是否已注册
    @GetMapping("checkRegister")
    public JsonResult checkRegister(String phone) {
        if (userInfoService.getByPhone(phone) != null) {
            return new JsonResult<>(HttpStatus.OK, false, "该手机号已被注册,请重新输入", "", null);
        }
        return new JsonResult<>(HttpStatus.OK, true, "该手机号未注册", "", null);
    }

    @GetMapping("/getTagsInfo")
    public List<CategoryTag> getTagsInfo() {
        return userInfoService.getTagsInfo();
    }

}
