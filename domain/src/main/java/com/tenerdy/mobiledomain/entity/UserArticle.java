package com.tenerdy.mobiledomain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserArticle {

  @TableId(type = IdType.AUTO,value = "article_id")
  private Integer articleId;
  private Integer userId;
  private String content;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date createTime;
  private Integer transfer;
  private Integer love;
  private Integer comment;
  @TableField(value = "`from`") // from是mysql关键字，需要转义
  private String from;
  @TableField(value = "`scope`") // scope是mysql关键字，需要转义
  private String scope;


}
