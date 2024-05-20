package com.tenerdy.mobileuserinfo.service;

import com.tenerdy.mobiledomain.entity.Tags;
import com.tenerdy.mobiledomain.entity.UserInfo;
import com.tenerdy.mobileuserinfo.bean.GroupInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserVO;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
     UserInfo getUserInfoByPhone(String phone);
     UserInfoVO getUserInfoByUserId(Integer mineId, Integer userId);
     List<Tags> getTagsByUserId(Integer userId);

    List<UserInfo> getAllUserInfoByUserId(Integer userId);


    Integer match(Integer userId);

    Map<String,Object>  matchMultiple(Integer userId);
    UserVO getFollowCount(Integer userId);


    UserInfo getEditUserInfoByUserId(Integer userId);

    void UpdateUserInfo(UserInfo userInfo);

    List<GroupInfoVO>  selectUserGroupByUserId(Integer userId);

    public Integer getIsBlack(Integer userId, Integer targetId);

    public void addBlack(Integer userId, Integer targetId);

    public List<Integer> getBlackList(Integer userId);


    public void changeBackground(Integer userId,String backgroundPicture);

    public Integer getIsFollow(Integer userId, Integer targetId);

    public void addWatch(Integer userId);
}
