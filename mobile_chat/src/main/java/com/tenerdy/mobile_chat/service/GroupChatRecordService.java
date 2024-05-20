package com.tenerdy.mobile_chat.service;

import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.GroupFileVO;
import com.tenerdy.mobiledomain.entity.UserInfo;

import java.util.Date;
import java.util.List;

public interface GroupChatRecordService {
    //发送群聊消息
    void sendGroupChatRecord(Integer fromId, Integer groupId, String content, Integer isread, Date time, Integer type);

    //获取群聊消息
    List<ChatRecordVO> getChatRecord(Integer fromId,Integer groupId);

    //设置群聊消息已读
    void setMessageRead(Integer groupId, Integer userId);

    List<Integer> getAllUser(Integer groupId);

    void SendInitMessage(Integer id,Integer groupId);

    void quitGroup(Integer userId, Integer groupId);

    List<UserInfo> getGroupMember(Integer groupId);

    List<GroupFileVO> selectGroupFilesByGroupId(int groupId);

}
