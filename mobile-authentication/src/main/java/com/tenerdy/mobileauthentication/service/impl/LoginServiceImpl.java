package com.tenerdy.mobileauthentication.service.impl;

import com.tenerdy.mobileauthentication.mapper.UserInfoMapper;
import com.tenerdy.mobileauthentication.service.LoginService;
import com.tenerdy.mobiledomain.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public boolean login(String phone, String password) {
        UserInfo user = userInfoMapper.findByPhone(phone);
        if(user == null)
            return false;
        return user.getPassword().equals(password);
    }
}
