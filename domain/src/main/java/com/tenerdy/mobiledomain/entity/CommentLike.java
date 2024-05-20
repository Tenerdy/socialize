package com.tenerdy.mobiledomain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLike {
    private int id;
    private int commentId;
    private int userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public CommentLike(int commentId, int userId, Date createTime) {
        this.commentId = commentId;
        this.userId = userId;
        this.createTime = createTime;
    }
}

