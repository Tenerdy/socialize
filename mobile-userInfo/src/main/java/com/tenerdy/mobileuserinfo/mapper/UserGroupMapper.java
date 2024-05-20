package com.tenerdy.mobileuserinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.UserGroup;
import com.tenerdy.mobileuserinfo.bean.GroupInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    @Select("SELECT chat_group.group_id, chat_group.group_name FROM user_group " +
            "JOIN chat_group ON user_group.group_id = chat_group.group_id " +
            "WHERE user_group.user_id = #{userId}")
    List<GroupInfoVO> selectUserGroupByUserId(Integer userId);


}
