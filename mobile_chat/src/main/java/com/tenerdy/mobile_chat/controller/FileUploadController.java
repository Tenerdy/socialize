package com.tenerdy.mobile_chat.controller;

import com.tenerdy.mobile_chat.service.SingleChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileUploadController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final String UPLOAD_FOLDER = "D:/uploads/";
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SingleChatRecordService singleChatRecordService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("newFilename") String newFilename) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("文件为空，请选择一个文件！", HttpStatus.BAD_REQUEST);
        }
        String token = request.getHeader("Authorization");
        String phone = "";
        if (null!=token && !token.isEmpty()) {
            String realToken = token.replaceFirst("bearer ", "");
            if (Boolean.TRUE.equals(redisTemplate.hasKey(realToken))) {
                phone = redisTemplate.opsForValue().get(realToken);
            }
        }
        try {
            // 如果目录不存在则创建上传目录
            File uploadFolder = new File(UPLOAD_FOLDER);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }


            //拼接文件名
            byte[] bytes = file.getBytes();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentDate = dateFormat.format(new Date());


            // 将文件保存到上传目录中
            Path path = Paths.get(UPLOAD_FOLDER + newFilename);
            Files.write(path, bytes);

            //通知双方


            return new ResponseEntity<>("文件上传成功！文件名：" + file.getOriginalFilename(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("文件上传失败！请重试！", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

