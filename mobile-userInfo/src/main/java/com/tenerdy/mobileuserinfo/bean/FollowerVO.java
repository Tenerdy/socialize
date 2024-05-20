package com.tenerdy.mobileuserinfo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerVO {

    private String nickname;
    private Integer count;
    private String avatar;
    private Integer userId;
}
