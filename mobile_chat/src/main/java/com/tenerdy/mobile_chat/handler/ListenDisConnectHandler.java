package com.tenerdy.mobile_chat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//监听channel断开连接
public class ListenDisConnectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 在这里删除断开连接的Channel
        Channel channelToRemove = ctx.channel();
        removeChannelFromMap(channelToRemove);

        // 调用父类方法以便其他处理程序也能收到channelInactive事件
        super.channelInactive(ctx);
    }

    private void removeChannelFromMap(Channel channelToRemove) {
        ChatServer.APP_USERS.values().removeIf(channel -> channel.equals(channelToRemove));
        ChatServer.SINGLE_CHAT_USERS.values().removeIf(channel -> channel.equals(channelToRemove));
    }
}

