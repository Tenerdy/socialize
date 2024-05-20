package com.tenerdy.mobile_chat.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Integer code;
    private Integer fromId;
    private Integer ToId;
    private String message;
    private Integer type;
    private Integer groupId;
    private byte[] voice;
}
