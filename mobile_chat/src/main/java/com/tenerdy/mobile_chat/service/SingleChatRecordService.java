package com.tenerdy.mobile_chat.service;

import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.LatestChatRecordVO;

import java.util.Date;
import java.util.List;

public interface SingleChatRecordService {

     void sendSingleChatRecord(Integer fromId,Integer toId,String content,Integer isread,Date time,Integer type);
     List<LatestChatRecordVO> getChatRecordByFromId(Integer fromId);
     List<ChatRecordVO> getChatRecord(Integer fromId, Integer toId);

     void setMessageRead(Integer fromId, Integer toId,Integer isread);

     List<LatestChatRecordVO> SearchChatByNickname(Integer fromId, String nickname);

     void SendMessage(Integer id,String message);

}
