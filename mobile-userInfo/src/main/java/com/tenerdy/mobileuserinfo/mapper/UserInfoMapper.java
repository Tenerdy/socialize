package com.tenerdy.mobileuserinfo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tenerdy.mobiledomain.entity.Tags;
import com.tenerdy.mobiledomain.entity.UserInfo;
import com.tenerdy.mobileuserinfo.bean.UserInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("select * from user_info where phone=#{phone}")
    UserInfo getUserInfoByPhone(String phone);

    @Select("SELECT u.user_id, u.nickname, u.picture, u.real_name, u.sex, u.birthday, u.email, u.phone, u.password, u.register_time, u.watch, CASE WHEN r.relation_id IS NOT NULL THEN true ELSE false END AS follow FROM user_info u LEFT JOIN user_relation r ON u.user_id = r.follower_id AND r.user_id = #{param1} WHERE u.user_id = #{param2}")
    UserInfoVO getUserInfoByUserId(Integer mineId, Integer userId);
    @Select("SELECT tags.id, tags.name FROM user_tags INNER JOIN tags ON user_tags.tag_id = tags.id WHERE user_tags.user_id = #{userId}")
    List<Tags> getTagsByUserId(Integer userId);

    @Select("SELECT * from user_info where user_id != #{userId}")
    List<UserInfo> getAllUserInfoByUserId(Integer userId);

    //random search in user_info
    @Select("SELECT user_id FROM user_info WHERE user_id != #{userId} ORDER BY RAND() LIMIT 1")
    Integer match(Integer userId);

    //随机匹配3个用户
    @Select("SELECT user_id FROM user_info WHERE user_id != #{userId} ORDER BY user_id ASC LIMIT 3")
    List<Integer> matchMultiple(Integer userId);

    @Select("SELECT " +
            "(SELECT COUNT(*) FROM user_relation WHERE user_id = #{userId}) as followingCount, " +
            "(SELECT COUNT(*) FROM user_relation WHERE follower_id = #{userId}) as followerCount, " +
            "watch " +
            "FROM user_info " +
            "WHERE user_id = #{userId}")
    UserVO getFollowCount(Integer userId);

    //select nickname by userId
    @Select("SELECT nickname FROM user_info WHERE user_id = #{userId}")
    String getNicknameByUserId(Integer userId);

    //select picture by userId
    @Select("SELECT picture FROM user_info WHERE user_id = #{userId}")
    String getPictureByUserId(Integer userId);

    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo getEditUserInfoByUserId(Integer userId);

    //查看是否拉黑了对方或者对方拉黑了自己,如果自己拉黑了对方返回1,如果对方拉黑了自己返回2,如果都没有返回0
    @Select("SELECT CASE WHEN (SELECT COUNT(*) FROM black_list WHERE user_id = #{param1} AND black_id = #{param2}) > 0 "+
            "THEN 1 WHEN (SELECT COUNT(*) FROM black_list "+
            "WHERE user_id = #{param2} AND black_id = #{param1}) > 0 THEN 2 ELSE 0 END AS isBlack")
    public Integer getIsBlack(Integer userId, Integer targetId);


    //返回我拉黑的所有人的id
    @Select("SELECT black_id FROM black_list WHERE user_id = #{userId}")
    public List<Integer> getBlackList(Integer userId);


    //返回我param1是否关注了对方param2, 如果关注了返回1，否则返回0
    @Select("SELECT CASE WHEN (SELECT COUNT(*) FROM user_relation WHERE user_id = #{param1} AND follower_id = #{param2}) > 0 THEN 1 ELSE 0 END AS isFollow")
    public Integer getIsFollow(Integer userId, Integer targetId);

    //watch+1
    @Update("UPDATE user_info SET watch = watch + 1 WHERE user_id = #{userId}")
    public void addWatch(Integer userId);
}
