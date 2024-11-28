package com.xuanxuan.elasticsearchdemos.demo3_Sync;

import cn.hutool.core.collection.CollUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * toKnow:
 *
 * 本 demo 仅作演示，实际运行需要连数据库
 *
 * 在项目时，每分钟增量同步数据库中近期修改的数据到 ES 中
 *
 * 仅展示 ES 相关的操作
 * 和数据库相关操作简化了，可以参考注释中的代码
 */

// todo 取消注释开启任务
// @Component
@Slf4j
public class IncSyncQuestionToEs {

//    @Resource
//    private QuestionMapper questionMapper;

    @Resource
    private QuestionESDAO questionESDAO;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 1) 查询近 5 分钟内修改的数据

        /**
         *     @Select("SELECT * FROM question WHERE updatetime >= #{minUpdateTime}")
         *     List<Question> listUpdatedQuestion(Date minUpdateTime);
         */
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        // List<Question> questionList = questionMapper.listUpdatedQuestion(fiveMinutesAgoDate);
        List<Question> questionList = new ArrayList<>();
        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc question");
            return;
        }

        // 2) 实体类转换为 ESDTO
        List<QuestionESDTO> questionEsDTOList = questionList.stream()
                .map(QuestionESDTO::objToDto)
                .collect(Collectors.toList());

        // 3) 分页批量保存到 ES
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("IncSyncQuestionToEs start, total {}", total);

        for (int i = 0; i < total; i += pageSize) {
            // 注意同步的数据下标不能超过总数据量
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            questionESDAO.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("IncSyncQuestionToEs end, total {}", total);
    }
}
