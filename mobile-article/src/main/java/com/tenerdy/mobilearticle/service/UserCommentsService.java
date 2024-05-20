package com.tenerdy.mobilearticle.service;

import com.tenerdy.mobilearticle.bean.CommentWithUserInfo;
import com.tenerdy.mobiledomain.entity.UserComments;

import java.util.List;

public interface UserCommentsService {
     List<CommentWithUserInfo> getCommentByArticleId(Integer articleId,Integer userId);

     void addParentArticle(UserComments userComments);


     int loveComment(Integer commentId,Integer userId);

     int unLoveComment(Integer commentId,Integer userId);
}
