package com.tenerdy.mobiledomain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePicture {
    @TableId(type= IdType.AUTO)
    Integer id;
    String url;
    Integer articleId;
}
