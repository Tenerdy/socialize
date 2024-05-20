package com.tenerdy.mobileuserinfo.controller;
import com.tenerdy.mobiledomain.bean.JsonResult;
import com.tenerdy.mobiledomain.entity.UserInfo;
import com.tenerdy.mobileuserinfo.bean.FollowerVO;
import com.tenerdy.mobileuserinfo.service.RelationService;
import com.tenerdy.mobileuserinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relation")
public class RelationController {

    @Autowired
    RelationService relationService;

    @PostMapping("/follow")
    public JsonResult follow(int userId, int followerId){
        relationService.follow(userId,followerId);
        return new JsonResult(HttpStatus.OK,true,"关注成功",null,null);
    }

    @PostMapping ("/cancelFollow")
    public JsonResult cancelFollow(int userId, int followerId){
        relationService.cancelFollow(userId,followerId);
        return new JsonResult(HttpStatus.OK,true,"取消关注成功",null,null);
    }

    @GetMapping ("/getFollowerByUserId")
    public List<FollowerVO> getFollowerByUserId(int userId){
        return relationService.getFollowerByUserId(userId);
    }


    @GetMapping ("/getFollowerByNickname")
    public List<FollowerVO> getFollowerByNickname(int userId, String nickname){
        return relationService.getFollowerByNickname(userId,nickname);
    }
    @GetMapping("/getFollowedByUserId")
    public List<FollowerVO> getFollowedByUserId(int userId){
        return relationService.getFollowedByUserId(userId);
    }
}
