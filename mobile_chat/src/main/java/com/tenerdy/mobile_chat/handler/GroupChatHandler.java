package com.tenerdy.mobile_chat.handler;

import com.alibaba.fastjson.JSON;
import com.tenerdy.mobile_chat.bean.Mapper.GroupChatRecordMapper;
import com.tenerdy.mobile_chat.bean.Mapper.GroupChatRecordReadMapper;
import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobile_chat.service.GroupChatRecordService;
import com.tenerdy.mobile_chat.util.SpringBeanUtil;
import com.tenerdy.mobiledomain.bean.JsonResult;
import com.tenerdy.mobiledomain.entity.GroupChatRead;
import com.tenerdy.mobiledomain.entity.GroupChatRecord;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class GroupChatHandler {

    public static final GroupChatRecordService service = SpringBeanUtil.getBean(GroupChatRecordService.class);

    public static final GroupChatRecordMapper groupChatRecordMapper = SpringBeanUtil.getBean(GroupChatRecordMapper.class);
    public static final GroupChatRecordReadMapper groupChatRecordReadMapper = SpringBeanUtil.getBean(GroupChatRecordReadMapper.class);
    public static void execute(ChannelHandlerContext ctx, RequestDto requestDto) {
        try{
            Date time = new Date();
            //无论接收方是否在线，都将消息发送给发送方
            ChatRecordVO chatRecordSend = ChatRecordVO.builder()
                    .chatType(1)
                    .fromId(requestDto.getFromId())
                    .content(requestDto.getMessage())
                    .isMine("mine")
                    .time(time)
                    .type(requestDto.getType())
                    .build();
            Channel channelSend = ChatServer.SINGLE_CHAT_USERS.get(requestDto.getFromId());
            //发送消息给发送方
            if(channelSend!=null&&channelSend.isActive())
            channelSend.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatRecordSend)));
            //将消息存入数据库
            GroupChatRecord groupChatRecord = GroupChatRecord.builder()
                    .fromId(requestDto.getFromId())
                    .groupId(requestDto.getToId())
                    .content(requestDto.getMessage())
                    .type(requestDto.getType())
                    .time(time)
                    .build();
            System.out.println(groupChatRecord.toString());
            groupChatRecordMapper.insert(groupChatRecord);

            String picture = groupChatRecordMapper.getPicture(requestDto.getFromId());

            //设置自己发送的消息为已读
            GroupChatRead groupChatRead = GroupChatRead.builder()
                    .recordId(groupChatRecord.getRecordId())
                    .userId(requestDto.getFromId())
                    .build();
            //这里的在线是指接收方是否在socketChat界面，如果不在socketChat界面，也算不在线
            //群聊要发送给群里的所有人，所以这里的userList是群里的所有人
            List<Integer> userList = service.getAllUser(requestDto.getToId());
            for (Integer userId : userList) {
                if(userId.equals(requestDto.getFromId()))
                    continue;
                Channel channel = ChatServer.SINGLE_CHAT_USERS.get(userId);
                if (null != channel && channel.isActive()) {
                    ChatRecordVO chatRecordVOReceive = ChatRecordVO.builder()
                            .chatType(1)
                            .content(requestDto.getMessage())
                            .fromId(requestDto.getFromId())
                            .isMine("others")
                            .time(time)
                            .picture(picture)
                            .type(requestDto.getType())
                            .build();
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(chatRecordVOReceive)));
                    //设置消息消息已读
                    GroupChatRead groupChatReadUser = GroupChatRead.builder()
                            .recordId(groupChatRecord.getRecordId())
                            .userId(userId)
                            .isread(1)
                            .build();
                    groupChatRecordReadMapper.insert(groupChatReadUser);
                }
                else{
                    //如果接收方不在线，设置消息未读,将消息直接存入数据库
                    GroupChatRead groupChatReadUser = GroupChatRead.builder()
                            .recordId(groupChatRecord.getRecordId())
                            .userId(userId)
                            .isread(0)
                            .build();
                    groupChatRecordReadMapper.insert(groupChatReadUser);
                    //如果接收方不在socketChat界面,但是app在线,那就发送通知
                    Channel appChannel = ChatServer.APP_USERS.get(userId);
                    if(null != appChannel && appChannel.isActive()){
                        appChannel.writeAndFlush(new TextWebSocketFrame("666"));
                    }
                }
            }
            if(null != ctx)
            ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.OK,true,"发送成功",null,null));
        }catch (Exception e) {
            if(null != ctx)
            ctx.channel().writeAndFlush(new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR,false,"发送失败",null,null));
        }
    }
}

