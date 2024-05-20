package com.tenerdy.mobilearticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.GroundArticleVO;
import com.tenerdy.mobiledomain.entity.UserArticle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserArticleMapper extends BaseMapper<UserArticle> {

    @Select("SET SESSION group_concat_max_len=18446744073709551615; SELECT ua.article_id, ua.user_id, ua.content, ua.create_time, ua.transfer, ua.love, ua.comment, ua.from, ua.scope, ua.category_id, ui.picture AS author_picture, ui.nickname AS username, " +
            "CASE WHEN ur.user_id IS NOT NULL THEN 1 ELSE 0 END AS follow, " +
            "CASE WHEN al.article_id IS NOT NULL THEN 1 ELSE 0 END AS isLove, " +
            "(SELECT GROUP_CONCAT(ap.url) FROM article_picture ap WHERE ap.article_id = ua.article_id) AS picUrlList " +
            "FROM user_article ua " +
            "JOIN user_info ui ON ua.user_id = ui.user_id " +
            "LEFT JOIN user_relation ur ON ua.user_id = ur.follower_id AND ur.user_id = #{userId} " +
            "LEFT JOIN article_like al ON ua.article_id = al.article_id AND al.user_id = #{userId} " +
            "WHERE ua.user_id = #{userId} " +
            "ORDER BY ua.create_time DESC")
    List<GroundArticleVO> getArticleByUserId(Integer userId);



    @Select("SET SESSION group_concat_max_len=18446744073709551615; SELECT ua.article_id, ua.user_id, ua.content, ua.create_time, ua.transfer, ua.love, ua.comment, ua.from, ua.scope, ua.category_id, ui.picture AS author_picture, ui.nickname AS username, " +
            "CASE WHEN ur.user_id IS NOT NULL THEN 1 ELSE 0 END AS follow, " +
            "CASE WHEN al.article_id IS NOT NULL THEN 1 ELSE 0 END AS isLove, " +
            "(SELECT GROUP_CONCAT(ap.url) FROM article_picture ap WHERE ap.article_id = ua.article_id) AS picUrlList " +
            "FROM user_article ua " +
            "JOIN user_info ui ON ua.user_id = ui.user_id " +
            "LEFT JOIN user_relation ur ON ua.user_id = ur.follower_id AND ur.user_id = #{userId} " +
            "LEFT JOIN article_like al ON ua.article_id = al.article_id AND al.user_id = #{userId} " +
            "WHERE ua.user_id NOT IN (SELECT black_id FROM black_list WHERE user_id = #{userId}) " +
            "ORDER BY ua.create_time DESC")
    List<GroundArticleVO> listAllArticle(int userId);





    @Select("SET SESSION group_concat_max_len=18446744073709551615; SELECT ua.article_id, ua.user_id, ua.content, ua.create_time, ua.transfer, ua.love, ua.comment, ua.from, ua.scope, ua.category_id, ui.picture AS author_picture, ui.nickname AS username, " +
            "CASE WHEN ur.user_id IS NOT NULL THEN 1 ELSE 0 END AS follow, " +
            "CASE WHEN al.article_id IS NOT NULL THEN 1 ELSE 0 END AS isLove, " +
            "(SELECT GROUP_CONCAT(ap.url) FROM article_picture ap WHERE ap.article_id = ua.article_id) AS picUrlList " +
            "FROM user_article ua " +
            "JOIN user_info ui ON ua.user_id = ui.user_id " +
            "LEFT JOIN user_relation ur ON ua.user_id = ur.follower_id AND ur.user_id = #{userId} " +
            "LEFT JOIN article_like al ON ua.article_id = al.article_id AND al.user_id = #{userId} " +
            "WHERE ua.user_id NOT IN (SELECT black_id FROM black_list WHERE user_id = #{userId}) AND ua.user_id != #{userId} " +
            "AND ua.user_id IN (SELECT follower_id FROM user_relation WHERE user_id = #{userId}) " +
            "ORDER BY ua.create_time DESC")
    List<GroundArticleVO> listAllFollowArticle(int userId);



    @Select("SET SESSION group_concat_max_len=18446744073709551615; SELECT ua.article_id AS articleId, ua.user_id AS userId, ua.content, ua.create_time AS createTime, ua.transfer, ua.love, ua.comment, ua.from, ua.scope, ua.category_id AS categoryId, ui.picture AS authorPicture, ui.nickname AS username, " +
            "CASE WHEN ur.follower_id IS NOT NULL THEN 1 ELSE 0 END AS follow, " +
            "CASE WHEN al.article_id IS NOT NULL THEN 1 ELSE 0 END AS isLove, " +
            "(SELECT GROUP_CONCAT(ap.url) FROM article_picture ap WHERE ap.article_id = ua.article_id) AS picUrlList " +
            "FROM user_article ua " +
            "JOIN user_info ui ON ua.user_id = ui.user_id " +
            "LEFT JOIN user_relation ur ON ua.user_id = ur.follower_id AND ur.follower_id = #{param2} " +
            "LEFT JOIN article_like al ON ua.article_id = al.article_id AND al.user_id = #{param2} " +
            "WHERE ua.article_id = #{param1} " +
            "ORDER BY createTime DESC LIMIT 1")
    GroundArticleVO getArticle(Integer articleId, Integer userId);

    //delete article and article_picture
    @Delete("DELETE FROM user_article WHERE article_id = #{articleId}; " +
            "DELETE FROM article_picture WHERE article_id = #{articleId};")
     void deleteArticle(Integer articleId);


    @Select("update user_article set love = love + 1 where article_id = #{articleId}")
     void incrementArticleLove(Integer articleId);

    //
    @Insert("INSERT INTO article_like (article_id, user_id, create_time) VALUES (#{param1}, #{param2}, NOW());" +
            "UPDATE user_article SET love = love + 1 WHERE article_id = #{param1};")
     int loveArticle(Integer articleId,Integer userId);

    @Delete("DELETE FROM article_like WHERE article_id = #{param1} AND user_id = #{param2};" +
            "UPDATE user_article SET love = GREATEST(love - 1, 0) WHERE article_id = #{param1};")
     int unLoveArticle(Integer articleId,Integer userId);
}
