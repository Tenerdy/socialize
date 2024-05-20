package com.tenerdy.mobile_chat.handler;
import com.alibaba.fastjson2.JSON;
import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobile_chat.bean.RequestType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * WebSocket处理器,也是处于整个处理链的第一个处理器
 * @author Tenerdy
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            handleTextWebSocketFrame(ctx, (TextWebSocketFrame) frame);
        } else if (frame instanceof BinaryWebSocketFrame) {
            handleBinaryWebSocketFrame(ctx, (BinaryWebSocketFrame) frame);
        } else {
            throw new UnsupportedOperationException("Unsupported WebSocketFrame: " + frame.getClass().getName());
        }
    }

        protected void handleTextWebSocketFrame(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
            RequestDto requestDto= JSON.parseObject(textWebSocketFrame.text(),RequestDto.class);
            switch (RequestType.getByCode(requestDto.getCode())) {
                //建立APP连接
                case APP_CONNECT : AppConnectHandler.execute(channelHandlerContext, requestDto); break;
                //建立私聊连接
                case CONNECT : ConnectionHandler.execute(channelHandlerContext, requestDto); break;
                //发送私聊消息
                case SINGLE_CHAT : SingleChatHandler.execute(channelHandlerContext, requestDto); break;
                //发送群聊消息
                case GROUP_CHAT : GroupChatHandler.execute(channelHandlerContext, requestDto); break;
                //加入群聊
                case JOIN_GROUP : JoinGroupHandler.execute(channelHandlerContext, requestDto); break;

                case StartVoice: StartVoiceHandler.execute(channelHandlerContext, requestDto); break;
            }
        }
    private void handleBinaryWebSocketFrame(ChannelHandlerContext ctx, BinaryWebSocketFrame binaryFrame) {
        ByteBuf content = binaryFrame.content();
        byte[] binaryData = new byte[content.readableBytes()];
        content.readBytes(binaryData);
        // 在这里处理音频数据
        System.out.println("收到语音消息，长度为：" + binaryData.length);
    }

}
