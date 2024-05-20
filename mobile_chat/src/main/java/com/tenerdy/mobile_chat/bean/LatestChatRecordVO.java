//package com.tenerdy.mobile_chat.bean;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class LatestChatRecordVO {
//    private Integer userId;
//    private String username;
//    private String avatar;
//    private Integer recordId;
//    private Integer fromId;
//    private Integer toId;
//    private String content;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    private Date time;
//    private Integer isread;
//    private Integer type;
//    private String picUrl;
//    private String fileUrl;
//    private String date;
//    private Integer countNotRead;
//    private Integer groupId;
//}
package com.tenerdy.mobile_chat.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestChatRecordVO {
    private Integer id;
    private String nickname;
    private String picture;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer countNotRead;
    private Integer type;
}