package com.tenerdy.mobile_chat.bean.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobile_chat.bean.ChatRecordVO;
import com.tenerdy.mobile_chat.bean.GroupFileVO;
import com.tenerdy.mobiledomain.entity.GroupChatRead;
import com.tenerdy.mobiledomain.entity.GroupChatRecord;
import com.tenerdy.mobiledomain.entity.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GroupChatRecordMapper extends BaseMapper<GroupChatRecord> {


    @Select("SELECT (CASE WHEN from_id = #{param1} THEN 'mine' ELSE 'others' END) as isMine, " +
            "content, time, type, user_info.picture as picture ,user_info.nickname as nickname " +
            "FROM group_chat_record " +
            "JOIN user_info ON group_chat_record.from_id = user_info.user_id " +
            "WHERE group_id = #{param2} " +
            "ORDER BY time ASC")
    List<ChatRecordVO> getChatRecord(Integer fromId, Integer groupId);


    @Update("UPDATE group_chat_read " +
            "SET isread = 1 " +
            "WHERE record_id IN ( " +
            "SELECT record_id FROM group_chat_record " +
            "WHERE group_id = #{param1} " +
            ") " +
            "AND user_id = #{param2}")
    int setMessageRead(Integer groupId, Integer userId);

    @Select("SELECT * FROM group_chat_read WHERE record_id = #{recordId}")
    List<GroupChatRead> getGroupChatRead(Integer recordId);


    @Select("SELECT user_id FROM user_group WHERE group_id = #{param1}")
    List <Integer> getAllUser(Integer groupId);

    @Update("UPDATE group_chat_read " +
            "SET isread = 0 " +
            "WHERE record_id = #{param1} " +
            "AND user_id = #{param2}")
    int setMessageOneNotRead(Integer recordId, Integer userId);

    @Update("UPDATE group_chat_read " +
            "SET isread = 1 " +
            "WHERE record_id = #{param1} " +
            "AND user_id = #{param2}")
    int setMessageOneRead(Integer recordId, Integer userId);

    //get picture from user_info by userId
    @Select("SELECT picture FROM user_info WHERE user_id = #{param1}")
    String getPicture(Integer userId);

    //退出群聊
    @Delete("DELETE FROM user_group WHERE user_id = #{param1} AND group_id = #{param2}")
    void quitGroup(Integer userId, Integer groupId);


    //get group member
    @Select("SELECT user_info.user_id as userId, user_info.nickname as nickname, user_info.picture as picture " +
            "FROM user_info " +
            "JOIN user_group ON user_info.user_id = user_group.user_id " +
            "WHERE user_group.group_id = #{groupId}")
    List<UserInfo> getGroupMember(Integer groupId);

    @Select("SELECT gcr.content AS filename, gcr.time AS time, ui.nickname AS nickname FROM group_chat_record gcr JOIN user_info ui ON gcr.from_id = ui.user_id WHERE gcr.group_id = #{groupId} AND gcr.type = 3")
    List<GroupFileVO> selectGroupFilesByGroupId(int groupId);
}
