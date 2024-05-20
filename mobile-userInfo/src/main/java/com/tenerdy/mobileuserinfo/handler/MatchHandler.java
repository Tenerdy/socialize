package com.tenerdy.mobileuserinfo.handler;
import java.util.List;
import java.util.PriorityQueue;

import com.tenerdy.mobiledomain.entity.UserTags;
import com.tenerdy.mobileuserinfo.mapper.UserTagsMapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class MatchHandler {
    @Autowired
    UserTagsMapper userTagsMapper;

//    @PostConstruct
    private  Map<Integer, List<Integer>> getUserVectorMap() {
        System.out.println("11111");
        // 从查询出所有的用户标签
        List<UserTags> userTags = userTagsMapper.selectAll();
        //保存所有有标签的用户的标签,去重
        Set<Integer> tagSet = new HashSet<>();
        for (UserTags userTag : userTags) {
            Integer tagId = userTag.getTagId();
            tagSet.add(tagId);
        }
        System.out.println("所有的标签有：" + tagSet);
        Map<Integer, List<Integer>> userVectorMap = new HashMap<>();
        // 遍历所有的用户
        for (UserTags userTag : userTags) {
            Integer userId = userTag.getUserId();
            Integer tagId = userTag.getTagId();

            // 如果 userVectorMap 中没有该用户的向量，则创建一个新向量并将其添加到 Map 中
            if (!userVectorMap.containsKey(userId)) {
                List<Integer> newUserVector = new ArrayList<>(Collections.nCopies(tagSet.size(), 0));
                userVectorMap.put(userId, newUserVector);
            }
            // 获取用户的向量并在对应的位置设置 1（表示有该标签）
            List<Integer> userVector = userVectorMap.get(userId);
            int tagIndex = new ArrayList<>(tagSet).indexOf(tagId);
            userVector.set(tagIndex, 1);
        }

        System.out.println("用户标签向量：" + userVectorMap);
        return userVectorMap;
    }

    //计算用户相似度
    public static double cosineSimilarity(List<Integer> vectorA, List<Integer> vectorB) {
        int dotProduct = 0;
        int normA = 0;
        int normB = 0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += vectorA.get(i) * vectorA.get(i);
            normB += vectorB.get(i) * vectorB.get(i);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // 查找与指定用户匹配的用户
    public List<Integer> findMatches(Integer currentUserId, int topK) {
        Map<Integer, List<Integer>> userVectorMap = getUserVectorMap();
        List<Integer> currentUserVector = userVectorMap.get(currentUserId);

        Queue<Pair<Integer, Double>> minHeap = new PriorityQueue<>(topK, Comparator.comparing(Pair::getValue));

        for (Map.Entry<Integer, List<Integer>> entry : userVectorMap.entrySet()) {
            if (!entry.getKey().equals(currentUserId)) {
                double similarity = cosineSimilarity(currentUserVector, entry.getValue());
                if (minHeap.size() < topK) {
                    minHeap.offer(new Pair<>(entry.getKey(), similarity));
                } else if (similarity > minHeap.peek().getValue()) {
                    minHeap.poll();
                    minHeap.offer(new Pair<>(entry.getKey(), similarity));
                }
            }
        }

        List<Integer> matches = new ArrayList<>(topK);
        while (!minHeap.isEmpty()) {
            matches.add(minHeap.poll().getKey());
        }
        return matches;
    }




}