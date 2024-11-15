package com.xuanxuan.redisdemo.demo5_ranklist;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class RanklistService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final String ranklistKey = "ranklist";

    /**
     * 向排行榜添加用户
     */
    public void addUser(Long userId, Double score) {
        redisTemplate.opsForZSet().addIfAbsent(ranklistKey, userId, score);
    }

    /**
     * 获取用户分数
     */
    public Double getScore(Long userId) {
        return redisTemplate.opsForZSet().score(ranklistKey, userId);
    }

    /**
     * 获取用户排名
     */
    public Long getRank(Long userId) {
        return redisTemplate.opsForZSet().reverseRank(ranklistKey, userId);
    }

    /**
     * 获取前 10 名用户
     */
    public Set<Object> getTop10Users() {
        return redisTemplate.opsForZSet().reverseRange(ranklistKey, 0, 9);
    }

    /**
     * 更新用户分数
     */
    public void increaseUserScore(Long userId, Double delta) {
        redisTemplate.opsForZSet().incrementScore(ranklistKey, userId, delta);
    }

    /**
     * 分页获取用户
     */
    public Set<Object> getUsersByPage(int curPage, int pageSize) {
        int st = (curPage - 1) * pageSize;
        int ed = st + pageSize - 1;
        return redisTemplate.opsForZSet().reverseRange(ranklistKey, st, ed);
    }
}
