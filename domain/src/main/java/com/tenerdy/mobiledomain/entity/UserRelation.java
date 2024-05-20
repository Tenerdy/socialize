package com.tenerdy.mobiledomain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRelation {
    private Integer relationId;
    private Integer userId;
    private Integer followerId;
}