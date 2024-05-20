package com.tenerdy.mobiledomain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroundArticleVO {

    private Integer articleId;
    private Integer userId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Integer transfer;
    private Integer love;
    private Integer comment;
    private String from;
    private String scope;
    private Integer categoryId;
    private String authorPicture;
    private String username;
    private Integer follow;
    private Integer isLove;
    private String picUrlList;
}

