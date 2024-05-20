package com.tenerdy.mobilearticle.service;

import com.tenerdy.mobiledomain.entity.GroundArticleVO;
import com.tenerdy.mobiledomain.entity.UserArticle;

import java.util.List;

public interface UserArticleService {
     List<GroundArticleVO> getArticleByUserId(Integer userId);
     List<GroundArticleVO> listAllArticle(int userId);
     void addArticle(UserArticle userArticle,List<String> picUrlList);

     void incrementArticleLove(Integer articleId);

     GroundArticleVO getArticle(Integer articleId,Integer userId);

     int loveArticle(Integer articleId,Integer userId);

     int unLoveArticle(Integer articleId,Integer userId);

     List<GroundArticleVO> listAllFollowArticle(Integer userId);

     void deleteArticle(Integer articleId);
}
