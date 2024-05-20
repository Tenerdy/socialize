package com.tenerdy.mobileuserinfo.controller;
import com.alibaba.fastjson.JSONArray;
import com.tenerdy.mobileuserinfo.bean.CategoryTag;
import com.tenerdy.mobileuserinfo.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagsService tagsService;

    @GetMapping("/getTagsInfoByUserId")
    public List<CategoryTag> getTagsInfoByUserId(Integer userId){
        return tagsService.getTagsInfoByUserId(userId);
    }

    @GetMapping("/updateTagsByUserId")
    public void updateTagsByUserId(@RequestParam("userId") Integer userId, @RequestParam("tags")String tags){
        List <Integer> tagList = JSONArray.parseArray(tags, Integer.class);
        tagsService.updateTagsByUserId(userId,tagList);
    }
}
