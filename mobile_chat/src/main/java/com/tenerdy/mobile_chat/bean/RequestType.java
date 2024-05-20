package com.tenerdy.mobile_chat.bean;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestType {

    APP_CONNECT(400),
    DISCONNECT_APP(401),
    CONNECT(500),
    DISCONNECT(501),
    JOIN_GROUP(600),
    SINGLE_CHAT(701),
    GROUP_CHAT(702),
    StartVoice(801),
    ERROR(999);
    private final Integer code;
    public static RequestType getByCode(Integer code) {
        for (RequestType requestType : RequestType.values()) {
            if (requestType.getCode().equals(code)) {
                return requestType;
            }
        }
        return ERROR;
    }
}
