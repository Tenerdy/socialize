package com.tenerdy.mobileuserinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.UserRelation;
import com.tenerdy.mobileuserinfo.bean.FollowerVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface RelationMapper extends BaseMapper<UserRelation>{


    @Delete("delete from user_relation where user_id=#{param1} and follower_id=#{param2}")
     int cancelFollow(int userId, int followerId);



    @Select("SELECT ui.nickname,ui.user_id AS userId, COUNT(ua.article_id) AS count, ui.picture AS avatar " +
            "FROM user_relation ur " +
            "JOIN user_info ui ON ur.follower_id = ui.user_id " +
            "LEFT JOIN user_article ua ON ur.follower_id = ua.user_id " +
            "WHERE ur.user_id = #{userId} " +
            "GROUP BY ur.follower_id")
     List<FollowerVO> getFollowerByUserId(int userId);

    //返回关注了我的人的列表
    @Select("SELECT ui.nickname,ui.user_id AS userId, COUNT(ua.article_id) AS count, ui.picture AS avatar " +
            "FROM user_relation ur " +
            "JOIN user_info ui ON ur.user_id = ui.user_id " +
            "LEFT JOIN user_article ua ON ur.user_id = ua.user_id " +
            "WHERE ur.follower_id = #{userId} " +
            "GROUP BY ur.user_id")
    List<FollowerVO> getFollowedByUserId(int userId);



    // 根据nickname模糊查询
    @Select("SELECT ui.nickname,ui.user_id AS userId, COUNT(ua.article_id) AS count, ui.picture AS avatar " +
            "FROM user_relation ur " +
            "JOIN user_info ui ON ur.follower_id = ui.user_id " +
            "LEFT JOIN user_article ua ON ur.follower_id = ua.user_id " +
            "WHERE ur.user_id = #{param1} AND ui.nickname LIKE CONCAT('%',#{param2},'%') " +
            "GROUP BY ur.follower_id")
    List<FollowerVO> getFollowerByNickname(int userId, String nickname);


    @Delete("delete from user_relation where user_id=#{param1} and follower_id=#{param2} or user_id=#{param2} and follower_id=#{param1}")
    void deleteByUserIdAndFollowerId(int userId, int followerId);
}
