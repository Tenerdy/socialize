package com.tenerdy.mobileuserinfo.service;
import com.tenerdy.mobileuserinfo.bean.CategoryTag;

import java.util.List;
public interface TagsService {
    public List<CategoryTag> getTagsInfoByUserId(Integer userId);

    public void updateTagsByUserId(Integer userId, List<Integer> tags);
}
