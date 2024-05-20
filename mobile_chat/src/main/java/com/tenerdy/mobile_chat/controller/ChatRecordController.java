package com.tenerdy.mobile_chat.controller;


import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.LatestChatRecordVO;
import com.tenerdy.mobile_chat.service.GroupChatRecordService;
import com.tenerdy.mobile_chat.service.SingleChatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatRecord")
public class ChatRecordController {

    @Autowired
    SingleChatRecordService singleChatRecordService;

    @Autowired
    GroupChatRecordService groupChatRecordService;

    @GetMapping("/getLatestChatRecord")
    public List<LatestChatRecordVO> getChatRecord(Integer fromId){
        return singleChatRecordService.getChatRecordByFromId(fromId);
    }

    @GetMapping("/SearchChatByNickname")
    public List<LatestChatRecordVO> SearchChatByNickname(Integer fromId, String nickname){
        return singleChatRecordService.SearchChatByNickname(fromId, nickname);
    }

    @GetMapping("/getSingleChatRecord")
    public List<ChatRecordVO> getSingleChatRecord(Integer fromId, Integer toId){
        return singleChatRecordService.getChatRecord(fromId,toId);
    }

    @GetMapping("/getGroupChatRecord")
    public List<ChatRecordVO> getGroupChatRecord(Integer fromId, Integer toId){
        return groupChatRecordService.getChatRecord(fromId,toId);
    }

    @GetMapping("/sendMessage")
    public void SendMessage(@RequestParam("id") Integer id, @RequestParam("message")String message){
        singleChatRecordService.SendMessage(id,message);
    }

    @GetMapping("/sendInitMessage")
    public void SendInitMessage(@RequestParam("id") Integer id, @RequestParam("groupId")Integer groupId){
        groupChatRecordService.SendInitMessage(id,groupId);
    }

    //退出群聊
    @GetMapping("/quitGroup")
    public void quitGroup(@RequestParam("userId") Integer userId, @RequestParam("groupId")Integer groupId){
        groupChatRecordService.quitGroup(userId,groupId);
    }

    //get group member
    @GetMapping("/getGroupMember")
    public List getGroupMember(@RequestParam("groupId")Integer groupId){
        return groupChatRecordService.getGroupMember(groupId);
    }

    //selectGroupFilesByGroupId
    @GetMapping("/selectGroupFilesByGroupId")
    public List selectGroupFilesByGroupId(@RequestParam("groupId")Integer groupId){
        return groupChatRecordService.selectGroupFilesByGroupId(groupId);
    }

}
