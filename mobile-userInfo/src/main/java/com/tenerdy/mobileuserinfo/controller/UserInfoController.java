package com.tenerdy.mobileuserinfo.controller;
import com.tenerdy.mobiledomain.entity.Tags;
import com.tenerdy.mobiledomain.entity.UserInfo;
import com.tenerdy.mobileuserinfo.bean.GroupInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserVO;
import com.tenerdy.mobileuserinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    private static final String UPLOAD_FOLDER = "D:/uploads/";
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/searchByPhone")
    public UserInfo searchByPhone(String phone){
        return userInfoService.getUserInfoByPhone(phone);
    }

    @GetMapping("/searchByUserId")
    public UserInfoVO getUserInfoByUserId(Integer mineId, Integer userId){
        return userInfoService.getUserInfoByUserId(mineId,userId);
    }
    @GetMapping("/getTagsByUserId")
    public List<Tags> getTagsByUserId(Integer userId){
        return userInfoService.getTagsByUserId(userId);
    }

    @RequestMapping("/getUserInfoByUserId")
    public List<UserInfo> getAllUserInfoByUserId(Integer userId){
        return userInfoService.getAllUserInfoByUserId(userId);
    }
    
    @RequestMapping("/match")
    public Integer match(Integer userId){
        return userInfoService.match(userId);
    }


    @RequestMapping("/matchMultiple")
    public Map<String,Object> matchMultiple(Integer userId){

        return userInfoService.matchMultiple(userId);
    }

    @RequestMapping("/getFollowCount")
    public UserVO getFollowCount(Integer userId){
        return userInfoService.getFollowCount(userId);
    }

    @RequestMapping("/getEditUserInfoByUserId")
    public UserInfo getEditUserInfoByUserId(Integer userId){
        return userInfoService.getEditUserInfoByUserId(userId);
    }



    @PostMapping("/updateUserInfo")
    public void UpdateUserInfo(@RequestParam("picture") String picture,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("nickname") String nickname,
                               @RequestParam("birthday")String birthday,
                               @RequestParam("sex")Integer sex,
                               @RequestParam("realName")String realName,
                                @RequestParam("email")String email,
                                @RequestParam("userId") Integer userId) throws ParseException {

        UserInfo userInfo;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdayDate = formatter.parse(birthday);
        //如果更新了头像
        if (!(picture.equals("null"))) {
            try {
                // 如果目录不存在则创建上传目录
                File uploadFolder = new File(UPLOAD_FOLDER);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }
                //拼接文件名
                byte[] bytes = file.getBytes();
                // 将文件保存到上传目录中
                Path path = Paths.get(UPLOAD_FOLDER + picture);
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
            userInfo = UserInfo.builder()
                    .userId(userId)
                    .nickname(nickname)
                    .sex(sex)
                    .birthday(birthdayDate)
                    .picture(picture)
                    .realName(realName)
                    .email(email)
                    .build();
        }else{
            userInfo = UserInfo.builder()
                    .userId(userId)
                    .nickname(nickname)
                    .sex(sex)
                    .birthday(birthdayDate)
                    .realName(realName)
                    .email(email)
                    .build();
        }
        userInfoService.UpdateUserInfo(userInfo);
    }

    @GetMapping("/selectUserGroupByUserId")

    public List<GroupInfoVO> selectUserGroupByUserId(Integer userId){
        return userInfoService.selectUserGroupByUserId(userId);
    }

    @GetMapping("/getIsBlack")
    public Integer getIsBlack(Integer userId, Integer targetId){
        return userInfoService.getIsBlack(userId,targetId);
    }

    @GetMapping("/addBlack")
    public void addBlack(Integer userId, Integer targetId){
        userInfoService.addBlack(userId,targetId);
    }

    @GetMapping("/getBlackList")
    public List<Integer> getBlackList(Integer userId){
        return userInfoService.getBlackList(userId);
    }


    @PostMapping("/changeBackground")
    public void changeBackground(@RequestParam("picture") MultipartFile file,Integer userId,String backgroundPicture){
        try {
            // 如果目录不存在则创建上传目录
            File uploadFolder = new File(UPLOAD_FOLDER);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            //拼接文件名
            byte[] bytes = file.getBytes();
            // 将文件保存到上传目录中
            Path path = Paths.get(UPLOAD_FOLDER + backgroundPicture);
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        userInfoService.changeBackground(userId,backgroundPicture);
    }

    @GetMapping("/isFollow")
    public Integer getIsFollow(Integer userId, Integer targetId){
        return userInfoService.getIsFollow(userId,targetId);
    }

    @GetMapping("/addWatch")
    public void addWatch(Integer userId){
        userInfoService.addWatch(userId);
    }
}
