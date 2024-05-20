package com.tenerdy.mobile_chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupFileVO {
    private String filename;
    private Date time;
    private String nickname;
}
