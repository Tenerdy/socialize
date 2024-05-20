package com.tenerdy.mobileauthentication.service.impl;

import com.tenerdy.mobileauthentication.bean.CategoryTag;
import com.tenerdy.mobileauthentication.mapper.UserInfoMapper;
import com.tenerdy.mobileauthentication.mapper.UserTagsMapper;
import com.tenerdy.mobileauthentication.service.UserInfoService;
import com.tenerdy.mobiledomain.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserinfoService implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserTagsMapper userTagsMapper;

    public UserInfo getByPhone(String phone){
        return userInfoMapper.findByPhone(phone);
    }
    public void register(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }

    public List<CategoryTag> getTagsInfo(){
        return userTagsMapper.getTagsInfo();
    }
}
