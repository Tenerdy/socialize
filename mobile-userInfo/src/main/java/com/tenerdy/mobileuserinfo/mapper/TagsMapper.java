package com.tenerdy.mobileuserinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.Tags;
import com.tenerdy.mobileuserinfo.bean.CategoryTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagsMapper extends BaseMapper<Tags> {


    @Select("SELECT tc.name as category, t.id as id, t.name as title, " +
            "CASE WHEN ut.id IS NOT NULL THEN true ELSE false END as selected " +
            "FROM tag_categories tc " +
            "JOIN tags t ON tc.id = t.category_id " +
            "LEFT JOIN user_tags ut ON t.id = ut.tag_id AND ut.user_id = #{userId} " +
            "ORDER BY tc.id, t.id")
    List<CategoryTag> getTagsInfoByUserId(Integer userId);


}
