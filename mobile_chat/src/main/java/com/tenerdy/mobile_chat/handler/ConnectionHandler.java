package com.tenerdy.mobile_chat.handler;

import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobile_chat.service.GroupChatRecordService;
import com.tenerdy.mobile_chat.service.SingleChatRecordService;
import com.tenerdy.mobile_chat.util.SpringBeanUtil;
import com.tenerdy.mobiledomain.bean.JsonResult;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.http.HttpStatus;

public class ConnectionHandler {
    public static final SingleChatRecordService singleChatRecordService  = SpringBeanUtil.getBean(SingleChatRecordService.class);
    public static final GroupChatRecordService groupChatRecordService = SpringBeanUtil.getBean(GroupChatRecordService.class);


    public static void execute(ChannelHandlerContext ctx, RequestDto requestDto){

        //将用户id和channel绑定
        ChatServer.SINGLE_CHAT_USERS.put(requestDto.getFromId(),ctx.channel());

        //如果不是群聊，则直接把消息记录设置为已读即可
        if(requestDto.getGroupId()==null){
            //找到这两个人的聊天记录
            singleChatRecordService.setMessageRead(requestDto.getFromId(),requestDto.getToId(),1);
            //如果是群聊，则执行加入群组的操作,并设置所有群聊消息为已读
        }else{
//            JoinGroupHandler.execute(ctx,requestDto);
            //设置所有群聊消息为已读
            groupChatRecordService.setMessageRead(requestDto.getGroupId(),requestDto.getFromId());
        }
        ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.OK,true,"连接成功",null,null));
    }
}
