package com.tenerdy.mobiledomain.bean;
import com.tenerdy.mobiledomain.entity.UserInfo;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult <T> {
    private HttpStatus code;
    private Boolean success;
    private String message;
    private String token;
    private UserInfo userInfo;
}