package com.tenerdy.mobilearticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobilearticle.bean.CommentWithUserInfo;
import com.tenerdy.mobiledomain.entity.UserComments;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCommentsMapper extends BaseMapper<UserComments> {

    @Select("SELECT uc.comment_id, uc.article_id, uc.user_id, uc.parent_comment_id, uc.content, uc.create_time, uc.like_count, ui.nickname, ui.picture, " +
            "CASE WHEN cl.comment_id IS NOT NULL THEN 1 ELSE 0 END AS isLove " +
            "FROM user_comments AS uc " +
            "JOIN user_info AS ui ON uc.user_id = ui.user_id " +
            "LEFT JOIN comment_like cl ON uc.comment_id = cl.comment_id AND cl.user_id = #{param2} " +
            "WHERE uc.article_id = #{param1}")
     List<CommentWithUserInfo> getCommentByArticleId(Integer articleId,Integer userId);


    @Insert("INSERT INTO comment_like (comment_id, user_id, create_time) VALUES (#{param1}, #{param2}, NOW());" +
            "UPDATE user_comments SET like_count = like_count + 1 WHERE comment_id = #{param1};")
     int loveComment(Integer articleId,Integer userId);

    @Delete("DELETE FROM comment_like WHERE comment_id = #{param1} AND user_id = #{param2};" +
            "UPDATE user_comments SET like_count = GREATEST(like_count - 1, 0) WHERE comment_id = #{param1};")
     int unLoveComment(Integer articleId,Integer userId);

}
