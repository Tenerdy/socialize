package com.tenerdy.mobilearticle.service.Impl;

import com.tenerdy.mobilearticle.bean.CommentWithUserInfo;
import com.tenerdy.mobilearticle.mapper.UserArticleMapper;
import com.tenerdy.mobilearticle.mapper.UserCommentsMapper;
import com.tenerdy.mobilearticle.service.UserCommentsService;
import com.tenerdy.mobiledomain.entity.UserArticle;
import com.tenerdy.mobiledomain.entity.UserComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserCommentsServiceImpl implements UserCommentsService {

    @Autowired
    private UserCommentsMapper userCommentsMapper;

    @Autowired
    private UserArticleMapper userArticleMapper;
    public List<CommentWithUserInfo> getCommentByArticleId(Integer articleId,Integer userId){
       return  userCommentsMapper.getCommentByArticleId(articleId,userId);
    }

    @Transactional
    public void addParentArticle(UserComments userComments){
        userComments.setCreateTime(new Date());
        userComments.setParentCommentId(null);
        userComments.setLikeCount(0);
        UserArticle userArticle = userArticleMapper.selectById(userComments.getArticleId());
        userArticle.setComment(userArticle.getComment()+1);
        userArticleMapper.updateById(userArticle);
        userCommentsMapper.insert(userComments);
    }



    @Transactional
    public int loveComment(Integer commentId,Integer userId){
        return userCommentsMapper.loveComment(commentId,userId);
    }
    @Transactional

    public int unLoveComment(Integer commentId,Integer userId){
        return userCommentsMapper.unLoveComment(commentId,userId);
    }
}
