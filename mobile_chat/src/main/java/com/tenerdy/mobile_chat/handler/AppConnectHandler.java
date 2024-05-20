package com.tenerdy.mobile_chat.handler;

import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobile_chat.service.SingleChatRecordService;
import com.tenerdy.mobile_chat.util.SpringBeanUtil;
import com.tenerdy.mobiledomain.bean.JsonResult;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.http.HttpStatus;

public class AppConnectHandler {
    public static final SingleChatRecordService service = SpringBeanUtil.getBean(SingleChatRecordService.class);

    public static void execute(ChannelHandlerContext ctx, RequestDto requestDto){
        ChatServer.APP_USERS.put(requestDto.getFromId(),ctx.channel());
        ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.OK,true,"app连接成功",null,null));
    }
}
