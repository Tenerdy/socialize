package com.tenerdy.mobileuserinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.UserTags;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTagsMapper extends BaseMapper<UserTags> {


    @Delete("delete from user_tags where user_id = #{userId}")
    void deleteTagsByUserId(Integer userId);

    @Select("select * from user_tags")
    List<UserTags> selectAll();

}
