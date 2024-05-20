package com.tenerdy.mobileuserinfo.service;
import com.tenerdy.mobileuserinfo.bean.FollowerVO;
import java.util.List;

public interface RelationService {
     void follow(int userId,int followerId);

     void cancelFollow(int userId,int followerId);
     List<FollowerVO> getFollowerByUserId(int userId);

     List<FollowerVO> getFollowerByNickname(int userId, String nickname);
     List<FollowerVO> getFollowedByUserId(int userId);



}
