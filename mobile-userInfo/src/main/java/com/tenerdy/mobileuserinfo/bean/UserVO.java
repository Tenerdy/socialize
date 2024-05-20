package com.tenerdy.mobileuserinfo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    Integer followingCount;
    Integer followerCount;
    Integer watch;
}
