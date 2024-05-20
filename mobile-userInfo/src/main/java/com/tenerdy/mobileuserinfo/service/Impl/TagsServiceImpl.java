package com.tenerdy.mobileuserinfo.service.Impl;

import com.tenerdy.mobiledomain.entity.UserTags;
import com.tenerdy.mobileuserinfo.bean.CategoryTag;
import com.tenerdy.mobileuserinfo.mapper.TagsCategoryMapper;
import com.tenerdy.mobileuserinfo.mapper.TagsMapper;
import com.tenerdy.mobileuserinfo.mapper.UserTagsMapper;
import com.tenerdy.mobileuserinfo.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {


    @Autowired
    TagsMapper tagsMapper;

    @Autowired
    UserTagsMapper userTagsMapper;

    @Autowired
    TagsCategoryMapper tagsCategoryMapper;

    public List<CategoryTag> getTagsInfoByUserId(Integer userId) {
        return tagsMapper.getTagsInfoByUserId(userId);
    }

    @Transactional
    public void updateTagsByUserId(Integer userId, List<Integer> tags){
        userTagsMapper.deleteTagsByUserId(userId);
        for(Integer tagId:tags){
            UserTags userTags = UserTags.builder().
                    userId(userId).
                    tagId(tagId).
                    build();
            userTagsMapper.insert(userTags);
        }
    }
}
