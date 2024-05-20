package com.tenerdy.mobileauthentication.service;

import com.tenerdy.mobileauthentication.bean.CategoryTag;
import com.tenerdy.mobiledomain.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
     UserInfo getByPhone(String phone);

     void register(UserInfo userInfo);

     List<CategoryTag> getTagsInfo();
}
