package com.tenerdy.mobile_chat.service.Impl;

import com.tenerdy.mobile_chat.bean.Mapper.UserInfoMapper;
import com.tenerdy.mobile_chat.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public String getPicture(Integer userId) {
        return userInfoMapper.getPicture(userId);
    }
}
