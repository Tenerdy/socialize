package com.tenerdy.mobile_chat.service.Impl;

import com.tenerdy.mobile_chat.bean.GroupFileVO;
import com.tenerdy.mobile_chat.bean.Mapper.GroupChatRecordMapper;
import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.RequestDto;
import com.tenerdy.mobile_chat.handler.GroupChatHandler;
import com.tenerdy.mobile_chat.service.GroupChatRecordService;
import com.tenerdy.mobiledomain.entity.GroupChatRecord;
import com.tenerdy.mobiledomain.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GroupChatRecordServiceImpl implements GroupChatRecordService {

    @Autowired
    GroupChatRecordMapper groupChatRecordMapper;
    @Override
    public void sendGroupChatRecord(Integer fromId, Integer groupId, String content, Integer isread, Date time, Integer type) {
        GroupChatRecord groupChatRecord = GroupChatRecord.builder()
                .fromId(fromId)
                .groupId(groupId)
                .content(content)
                .type(type)
                .time(new Date())
                .build();

    }

    @Override
    public List<ChatRecordVO> getChatRecord(Integer fromId,Integer groupId) {
        return groupChatRecordMapper.getChatRecord(fromId,groupId);
    }

    @Override
    public void setMessageRead(Integer groupId, Integer userId) {
        groupChatRecordMapper.setMessageRead(groupId,userId);
    }
    @Override
    public List<Integer> getAllUser(Integer groupId) {
        return groupChatRecordMapper.getAllUser(groupId);
    }

    public void SendInitMessage(Integer id,Integer groupId){
        RequestDto requestDto = RequestDto.builder()
                .fromId(id)
                .message("我发起了群聊匹配,一起来聊天吧!")
                .type(1)
                .ToId(groupId)
                .build();
        GroupChatHandler.execute(null,requestDto);
    }


    public void quitGroup(Integer userId, Integer groupId){
        groupChatRecordMapper.quitGroup(userId,groupId);
    }
    public List<UserInfo> getGroupMember(Integer groupId){
        return groupChatRecordMapper.getGroupMember(groupId);
    }

    public List<GroupFileVO> selectGroupFilesByGroupId(int groupId){
        return groupChatRecordMapper.selectGroupFilesByGroupId(groupId);
    }
}
