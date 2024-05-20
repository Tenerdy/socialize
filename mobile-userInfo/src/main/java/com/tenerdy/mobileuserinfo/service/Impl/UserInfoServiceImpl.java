package com.tenerdy.mobileuserinfo.service.Impl;

import com.tenerdy.mobiledomain.entity.*;
import com.tenerdy.mobileuserinfo.bean.GroupInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserInfoVO;
import com.tenerdy.mobileuserinfo.bean.UserVO;
import com.tenerdy.mobileuserinfo.feign.ChatFeign;
import com.tenerdy.mobileuserinfo.handler.MatchHandler;
import com.tenerdy.mobileuserinfo.mapper.*;
import com.tenerdy.mobileuserinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

   @Autowired
    private UserInfoMapper userInfoMapper;

   @Autowired
   private ChatGroupMapper chatGroupMapper;

   @Autowired
   private UserGroupMapper userGroupMapper;

   @Autowired
   private BlackListMapper blackListMapper;

   @Autowired
   private RelationMapper relationMapper;

   @Autowired
    ChatFeign chatFeign;

    @Autowired
    MatchHandler matchHandler;


   public UserInfo getUserInfoByPhone(String phone){
        return userInfoMapper.getUserInfoByPhone(phone);
    }

    public UserInfoVO getUserInfoByUserId(Integer mineId, Integer userId){
        return userInfoMapper.getUserInfoByUserId(mineId,userId);
    }
    public List<Tags> getTagsByUserId(Integer userId){
        return userInfoMapper.getTagsByUserId(userId);
    }

    public List<UserInfo> getAllUserInfoByUserId(Integer userId) {
        return userInfoMapper.getAllUserInfoByUserId(userId);
    }

    public Integer match(Integer userId){
//       return userInfoMapper.match(userId);
         List <Integer> list = matchHandler.findMatches(userId,1);
         return list.get(0);
    }



    public Map<String,Object> matchMultiple(Integer userId){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(date);
        String groupName = "匹配群聊" + formattedDate;
        Random random = new Random();
        //生成一个随机群号
        Integer randomNumber = random.nextInt(8899) + 1111;
        ChatGroup chatGroupRandom = chatGroupMapper.selectById(randomNumber);
        //如果群号已存在，重新生成
        while(chatGroupRandom != null){
            randomNumber = random.nextInt(8899) + 1111;
        }
        ChatGroup chatGroup = ChatGroup.builder()
                .groupId(randomNumber)
                .groupName(groupName)
                .build();
        //创建群聊
        chatGroupMapper.insert(chatGroup);
//        List<Integer> list =  userInfoMapper.matchMultiple(userId);

        List <Integer> list = matchHandler.findMatches(userId,3);
        System.out.println("匹配到的用户:"+list.toString());


        List<Map<String,Object>> userList = new ArrayList<>();
        System.out.println("用户列表:"+list.toString());
        //将匹配到的用户加入群聊
        for (Integer id : list) {
            Map<String,Object> map = new HashMap<>();
            String nickname = userInfoMapper.getNicknameByUserId(id);
            String picture = userInfoMapper.getPictureByUserId(id);
            map.put("userId",id);
            map.put("nickname",nickname);
            map.put("picture",picture);
            userList.add(map);
            UserGroup userGroup = UserGroup.builder()
                    .groupId(randomNumber)
                    .userId(id)
                    .build();
            userGroupMapper.insert(userGroup);

            chatFeign.SendMessage(id,"您已被匹配到群聊" + groupName + "，群号为" + randomNumber);
        }

        //将自己加入群聊
        UserGroup userGroup = UserGroup.builder()
                .groupId(randomNumber)
                .userId(userId)
                .build();

        userGroupMapper.insert(userGroup);
        chatFeign.SendInitMessage(userId,randomNumber);
        Map<String,Object> map = new HashMap<>();
        map.put("groupId",randomNumber);
        map.put("groupName",groupName);
        map.put("list",userList);

        return map;
    }

    public UserVO getFollowCount(Integer userId){
        return userInfoMapper.getFollowCount(userId);
    }


    public UserInfo getEditUserInfoByUserId(Integer userId){
        return userInfoMapper.getEditUserInfoByUserId(userId);
    }

    public void UpdateUserInfo(UserInfo userInfo){
        userInfoMapper.updateById(userInfo);
    }

    public List<GroupInfoVO>  selectUserGroupByUserId(Integer userId){
        return userGroupMapper.selectUserGroupByUserId(userId);
    }

    public Integer getIsBlack(Integer userId, Integer targetId){
        return userInfoMapper.getIsBlack(userId,targetId);
    }

    public void addBlack(Integer userId, Integer targetId){
        BlackList blackList = BlackList.builder()
                .userId(userId)
                .blackId(targetId)
                .build();
        blackListMapper.insert(blackList);
        //同时删除两人之间的关注关系
        relationMapper.deleteByUserIdAndFollowerId(userId,targetId);
    }

    public List<Integer> getBlackList(Integer userId){
        return userInfoMapper.getBlackList(userId);
    }

    public void changeBackground(Integer userId,String backgroundPicture){
        UserInfo userinfo = userInfoMapper.selectById(userId);
        userinfo.setBackgroundPicture(backgroundPicture);
        userInfoMapper.updateById(userinfo);
    }


    public Integer getIsFollow(Integer userId, Integer targetId){
       return userInfoMapper.getIsFollow(userId,targetId);
    }
    public void addWatch(Integer userId){
        userInfoMapper.addWatch(userId);
    }
}
