package com.xuanxuan.redisdemo.demo5_ranklist;

import cn.hutool.json.JSONObjectIter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class RankListTest {

    @Resource
    private RanklistService ranklistService;

    @Test
    public void test() {
        Long userId = 5L;
        Double score = 1000.0;
        ranklistService.addUser(userId, score);

        ranklistService.increaseUserScore(1L, 200.0);

        System.out.println("UserId: 1: Score" + ranklistService.getScore(1L)
                + " Rank: " + ranklistService.getRank(1L));

        Set<Object> top10UsersSet = ranklistService.getTop10Users();
        List<Long> top10Users = top10UsersSet.stream()
                .map(s -> Long.valueOf(s.toString()))
                .collect(Collectors.toList());

        System.out.println("Top 10 usersId List: " + top10Users);

        int curPage = 0, pageSize = 2;
        List<Long> pageUsers = ranklistService.getUsersByPage(curPage, pageSize).stream()
                .map(s -> Long.valueOf(s.toString()))
                .collect(Collectors.toList());

        System.out.println("Page 1 usersId List: " + pageUsers);
    }
}
