package com.tenerdy.mobile_chat.handler;
import com.alibaba.fastjson.JSON;
import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobile_chat.bean.ResponseVO;
import com.tenerdy.mobile_chat.service.SingleChatRecordService;
import com.tenerdy.mobile_chat.service.UserInfoService;
import com.tenerdy.mobile_chat.util.SpringBeanUtil;
import com.tenerdy.mobiledomain.bean.JsonResult;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Slf4j
public class SingleChatHandler {
    public static final SingleChatRecordService chatService = SpringBeanUtil.getBean(SingleChatRecordService.class);


    public static final UserInfoService userInfoService = SpringBeanUtil.getBean(UserInfoService.class);
    public static void execute(ChannelHandlerContext ctx, RequestDto requestDto){
        try{
            Channel channel = ChatServer.SINGLE_CHAT_USERS.get(requestDto.getToId());
            Date time = new Date();
            String picture = userInfoService.getPicture(requestDto.getFromId());
            //无论接收方是否在线，都将消息发送给发送方
            ChatRecordVO chatRecordSend = ChatRecordVO.builder()
                    .chatType(0)
                    .content(requestDto.getMessage())
                    .fromId(requestDto.getFromId())
                    .isMine("mine")
                    .time(time)
                    .type(requestDto.getType())
                    .build();
            log.info(requestDto.toString());
            log.info(chatRecordSend.toString());
            Channel channelSend = ChatServer.SINGLE_CHAT_USERS.get(requestDto.getFromId());
            channelSend.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatRecordSend)));
            //这里的在线是指接收方是否在socketChat界面，如果不在socketChat界面，也算不在线
            if (null != channel && channel.isActive()) {
                ChatRecordVO chatRecordVOReceive = ChatRecordVO.builder()
                        .chatType(0)
                        .content(requestDto.getMessage())
                        .fromId(requestDto.getFromId())
                        .isMine("others")
                        .time(time)
                        .type(requestDto.getType())
                        .picture(picture)
                        .build();
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatRecordVOReceive)));
                //设置对方消息已读,并将消息存入数据库
                chatService.sendSingleChatRecord(requestDto.getFromId(),requestDto.getToId(),requestDto.getMessage(),1,time,requestDto.getType());
            }
            else{
                //如果接收方不在线，设置消息未读,将消息直接存入数据库
                chatService.sendSingleChatRecord(requestDto.getFromId(),requestDto.getToId(),requestDto.getMessage(),0,time,requestDto.getType());
                //如果接收方不在socketChat界面,但是app在线,那就发送通知
                Channel appChannel = ChatServer.APP_USERS.get(requestDto.getToId());
                if(null != appChannel && appChannel.isActive()){
                    appChannel.writeAndFlush(new TextWebSocketFrame("666"));
                }

            }
            ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.OK,true,"发送成功",null,null));
        }catch (Exception e) {
            ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR,false,"发送失败",null,null));
        }
    }
}
