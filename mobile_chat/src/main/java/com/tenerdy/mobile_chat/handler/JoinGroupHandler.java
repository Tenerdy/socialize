package com.tenerdy.mobile_chat.handler;

import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobiledomain.bean.JsonResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.http.HttpStatus;


//在加入群组之前,需要已经建立连接ConnectionHandler
public class JoinGroupHandler {
    public static ChannelGroup getChannelGroup(Integer groupId) {
        ChannelGroup channelGroup = ChatServer.GROUP_MAP.get(groupId);
        //双检锁
        if (channelGroup == null) {
            synchronized (ChatServer.GROUP_MAP) {
                // 再次检查，以防在等待锁的过程中已经被其他线程创建
                channelGroup = ChatServer.GROUP_MAP.get(groupId);
                if (channelGroup == null) {
                    // 创建一个channelGroup
                    channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                    // 将channelGroup放入map中
                    ChatServer.GROUP_MAP.put(groupId, channelGroup);
                }
            }
        }
        return channelGroup;
    }

    public static void execute(ChannelHandlerContext ctx, RequestDto requestDto){
        //先通过群号拿到这个群的channelGroup,使用自定义的带双检锁的方法
        ChannelGroup group = getChannelGroup(requestDto.getGroupId());
        //将用户的channel加入到这个群组中
        group.add(ctx.channel());
        //将群号和channelGroup放入map中
        ChatServer.GROUP_MAP.put(requestDto.getGroupId(),group);
        ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.OK,true,"加入群组成功",null,null));

    }
}
