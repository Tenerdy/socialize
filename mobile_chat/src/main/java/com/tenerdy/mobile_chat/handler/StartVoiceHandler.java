package com.tenerdy.mobile_chat.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.tenerdy.mobile_chat.bean.RequestDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Base64;

public class StartVoiceHandler {
    public static void execute(ChannelHandlerContext ctx, RequestDto requestDto){
//        byte[] audioData = Base64.getDecoder().decode(requestDto.getVoice());

        System.out.println("来自"+requestDto.getFromId()+"的语音消息：");
        System.out.println("语音消息原始数据:"+requestDto.getVoice());
    }
}
