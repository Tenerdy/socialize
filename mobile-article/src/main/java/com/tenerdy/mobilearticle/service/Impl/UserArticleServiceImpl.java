package com.tenerdy.mobilearticle.service.Impl;

import com.tenerdy.mobilearticle.mapper.ArticlePictureMapper;
import com.tenerdy.mobilearticle.mapper.UserArticleMapper;
import com.tenerdy.mobilearticle.service.UserArticleService;
import com.tenerdy.mobiledomain.entity.ArticlePicture;
import com.tenerdy.mobiledomain.entity.GroundArticleVO;
import com.tenerdy.mobiledomain.entity.UserArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class UserArticleServiceImpl implements UserArticleService {

    @Autowired
    UserArticleMapper userArticleMapper;

    @Autowired
    ArticlePictureMapper articlePictureMapper;
    public List<GroundArticleVO> getArticleByUserId(Integer userId){
        return userArticleMapper.getArticleByUserId(userId);
    }
    public List<GroundArticleVO> listAllArticle(int userId){
        List<GroundArticleVO> result;
        result = userArticleMapper.listAllArticle(userId);
        return userArticleMapper.listAllArticle(userId);
    }


    public void addArticle(UserArticle userArticle,List<String> picUrlList){
        userArticle.setCreateTime(new Date());
        userArticle.setTransfer(0);
        userArticle.setLove(0);
        userArticle.setComment(0);
        userArticle.setFrom("个人动态");
        userArticle.setScope("0");
        userArticleMapper.insert(userArticle);
        int articleId = userArticle.getArticleId();
        for (String picUrl:picUrlList){
            ArticlePicture articlePicture = ArticlePicture.builder()
                    .articleId(articleId)
                    .url(picUrl)
                    .build();
            articlePictureMapper.insert(articlePicture);
        }

    }

    public void incrementArticleLove(Integer articleId){
        userArticleMapper.incrementArticleLove(articleId);
    }


    public GroundArticleVO getArticle(Integer articleId ,Integer userId){
        return userArticleMapper.getArticle(articleId,userId);
    }


    @Transactional
    public int loveArticle(Integer articleId,Integer userId){
        return userArticleMapper.loveArticle(articleId,userId);
    }

    @Transactional
    public int unLoveArticle(Integer articleId,Integer userId){
        return userArticleMapper.unLoveArticle(articleId,userId);
    }

    public List<GroundArticleVO> listAllFollowArticle(Integer userId){
        return userArticleMapper.listAllFollowArticle(userId);
    }

    public void deleteArticle(Integer articleId){
        userArticleMapper.deleteArticle(articleId);
    }
}
