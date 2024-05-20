package com.tenerdy.mobileauthentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobileauthentication.bean.CategoryTag;
import com.tenerdy.mobiledomain.entity.UserTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTagsMapper extends BaseMapper<UserTags> {
    @Select("SELECT tc.name as category, t.id as id, t.name as title " +
            "FROM tag_categories tc " +
            "JOIN tags t ON tc.id = t.category_id " +
            "ORDER BY tc.id, t.id")
    List<CategoryTag> getTagsInfo();

}
