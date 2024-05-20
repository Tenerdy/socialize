package com.tenerdy.mobile_chat.service.Impl;

import com.tenerdy.mobile_chat.bean.Mapper.SingleChatRecordMapper;
import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.LatestChatRecordVO;
import com.tenerdy.mobile_chat.handler.ChatServer;
import com.tenerdy.mobile_chat.service.SingleChatRecordService;
import com.tenerdy.mobiledomain.entity.SingleChatRecord;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
public class SingleChatRecordServiceImpl implements SingleChatRecordService{
    @Autowired
    private SingleChatRecordMapper singleChatRecordMapper;

    public void sendSingleChatRecord(Integer fromId,Integer toId,String content,Integer isread,Date time,Integer type){
        SingleChatRecord singleChatRecord = SingleChatRecord.builder()
                .fromId(fromId)
                .toId(toId)
                .content(content)
                .type(type)
                .time(new Date())
                .isread(isread)
                .build();
        //设置接收方消息未读状态
        singleChatRecordMapper.insert(singleChatRecord);
    }
    public List<LatestChatRecordVO> getChatRecordByFromId(Integer fromId){
        return singleChatRecordMapper.getChatRecordByFromId(fromId);
    }
    public List<ChatRecordVO> getChatRecord(Integer fromId, Integer toId){
        return singleChatRecordMapper.getChatRecord(fromId,toId);
    }

    public void setMessageRead(Integer fromId, Integer toId,Integer isread){
        singleChatRecordMapper.setMessageRead(fromId, toId,isread);
    }

    public List<LatestChatRecordVO> SearchChatByNickname(Integer fromId, String nickname){
        return singleChatRecordMapper.SearchChatByNickname(fromId, nickname);
    }

    public void SendMessage(Integer id,String message){
        Channel channel = ChatServer.APP_USERS.get(id);
        if(channel!=null&&channel.isActive()){
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }


}
