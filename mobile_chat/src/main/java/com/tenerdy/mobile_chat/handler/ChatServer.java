package com.tenerdy.mobile_chat.handler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ChatServer {

    //存储所有的私聊channel与用户的对应关系
    public static final Map<Integer, Channel> SINGLE_CHAT_USERS= new ConcurrentHashMap<>();
    //保存app用户的channel,用于推送消息
    public static final Map<Integer, Channel> APP_USERS= new ConcurrentHashMap<>();

    //保存群号与群channel的对应关系
    public static final Map<Integer, ChannelGroup> GROUP_MAP= new ConcurrentHashMap<>();

    //使用注解@PostConstruct，在项目启动的时候执行该方法
    @PostConstruct
    public void execute() {
        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //绑定两个线程组
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //websocket基于http协议，所以要有http编解码器
                        pipeline.addLast(new HttpServerCodec())
                                //对写大数据流的支持
                                .addLast(new ChunkedWriteHandler())
                                //对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
                                .addLast(new HttpObjectAggregator(65536 * 100))
                                .addLast(new WebSocketServerProtocolHandler("/", null, true, 65536 * 100))
                                //自定义的handler
                                .addLast(new WebSocketHandler())
                                .addLast(new ListenDisConnectHandler());

                    }
                });
        try {
            serverBootstrap.bind(8090).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
