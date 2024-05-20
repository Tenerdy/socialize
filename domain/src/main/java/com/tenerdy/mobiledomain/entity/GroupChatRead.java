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
public class GroupChatRead {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer recordId;
    private Integer userId;
    private Integer isread;
}
