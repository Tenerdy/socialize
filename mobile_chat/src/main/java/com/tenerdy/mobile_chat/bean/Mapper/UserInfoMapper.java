package com.tenerdy.mobile_chat.bean.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("select picture from user_info where user_id = #{userId}")
    String getPicture(Integer userId);
}
