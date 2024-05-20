package com.tenerdy.mobileuserinfo.service.Impl;

import com.tenerdy.mobiledomain.entity.UserInfo;
import com.tenerdy.mobiledomain.entity.UserRelation;
import com.tenerdy.mobileuserinfo.bean.FollowerVO;
import com.tenerdy.mobileuserinfo.mapper.RelationMapper;
import com.tenerdy.mobileuserinfo.mapper.UserInfoMapper;
import com.tenerdy.mobileuserinfo.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationMapper relationMapper;
    public void follow(int userId,int followerId){
        UserRelation userRelation = UserRelation.builder()
                .userId(userId)
                .followerId(followerId)
                .build();
        relationMapper.insert(userRelation);
    }

    public void cancelFollow(int userId,int followerId){
        relationMapper.cancelFollow(userId,followerId);
    }

    public List<FollowerVO> getFollowerByUserId(int userId){
        return relationMapper.getFollowerByUserId(userId);
    }
    public List<FollowerVO> getFollowerByNickname(int userId, String nickname){
        return relationMapper.getFollowerByNickname(userId,nickname);
    }

    public List<FollowerVO> getFollowedByUserId(int userId){
        return relationMapper.getFollowedByUserId(userId);
    }
}
