package com.tenerdy.mobileauthentication.config;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.NettyCustomizer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public ClientResources clientResources(){

        NettyCustomizer nettyCustomizer = new NettyCustomizer() {

            @Override
            public void afterChannelInitialized(Channel channel) {
                channel.pipeline().addLast(
                        //此处事件必须小于超时时间
                        new IdleStateHandler(40, 0, 0));
                channel.pipeline().addLast(new ChannelDuplexHandler() {
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        if (evt instanceof IdleStateEvent) {
                            System.out.println("正在利用心跳机制维持与redis的连接，每隔40秒执行一次");
                            ctx.writeAndFlush("PING");
                            ctx.disconnect();
                        }
                    }
                });
            }

            @Override
            public void afterBootstrapInitialized(Bootstrap bootstrap) {

            }

        };

        return ClientResources.builder().nettyCustomizer(nettyCustomizer ).build();
    }
}
