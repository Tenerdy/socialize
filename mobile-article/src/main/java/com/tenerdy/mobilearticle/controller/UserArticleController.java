package com.tenerdy.mobilearticle.controller;


import com.alibaba.fastjson.JSON;
import com.tenerdy.mobilearticle.service.UserArticleService;
import com.tenerdy.mobilearticle.service.UserCommentsService;
import com.tenerdy.mobilearticle.bean.CommentWithUserInfo;
import com.tenerdy.mobiledomain.entity.GroundArticleVO;
import com.tenerdy.mobiledomain.entity.UserArticle;
import com.tenerdy.mobiledomain.entity.UserComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserArticleController {

    @Autowired
    UserArticleService userArticleService;

    @Autowired
    UserCommentsService userCommentsService;

    @RequestMapping("/getArticleByUserId")
    public List<GroundArticleVO> getArticleByUserId(Integer userId){
        return userArticleService.getArticleByUserId(userId);
    }
    @RequestMapping("/listAllArticle")
    public List<GroundArticleVO> listAllArticle(int userId){
        List<GroundArticleVO> result;
        result = userArticleService.listAllArticle(userId);
        return result;
    }

    @RequestMapping("/getCommentByArticleId")
    public List<CommentWithUserInfo> getCommentByArticleId(Integer articleId,Integer userId){
        return userCommentsService.getCommentByArticleId(articleId,userId);
    }

    @RequestMapping("/getArticle")
    public GroundArticleVO getArticle(Integer articleId,Integer userId){
        return userArticleService.getArticle(articleId,userId);
    }

    @RequestMapping("/addParentComment")
    public void addComment(UserComments userComments){
        userCommentsService.addParentArticle(userComments);
    }

    @PostMapping("/addArticle")
    public void addArticle(String content,Integer userId,String picUrls){
        List<String> picUrlList = JSON.parseArray(picUrls, String.class);
        UserArticle userArticle= UserArticle.builder()
                .userId(userId)
                .content(content)
                .build();
        userArticleService.addArticle(userArticle,picUrlList);
    }


    @RequestMapping("/loveArticle")
    public void loveArticle(Integer articleId,Integer userId){
        userArticleService.loveArticle(articleId,userId);
    }

    @RequestMapping("/unLoveArticle")
    public void unLoveArticle(Integer articleId,Integer userId){
        userArticleService.unLoveArticle(articleId,userId);
    }

    @RequestMapping("/loveComment")
    public void loveComment(Integer commentId,Integer userId){
        userCommentsService.loveComment(commentId,userId);
    }


    @RequestMapping("/listAllFollowArticle")
    public List<GroundArticleVO> listAllFollowArticle(Integer userId){
        return userArticleService.listAllFollowArticle(userId);
    }

    @RequestMapping("/unLoveComment")
    public void unLoveComment(Integer commentId,Integer userId){
        userCommentsService.unLoveComment(commentId,userId);
    }

    @RequestMapping("/deleteArticle")
    public void deleteArticle(Integer articleId){
        userArticleService.deleteArticle(articleId);
    }
}
