package com.xuanxuan.elasticsearchdemos.demo3_Sync;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * toKnow:
 *
 * 本 demo 仅作演示，实际运行需要连数据库
 *
 * 在项目开启之前，只运行一次，全量同步数据库中数据到 ES 中
 *
 * 仅展示 ES 相关的操作
 * 和数据库相关操作简化了，可以参考注释中的代码
 */

// todo 取消注释开启任务
// @Component
@Slf4j
// CommandLineRunner 在开启项目时执行一次
public class FullSyncQuestionToEs implements CommandLineRunner {

//    @Resource
//    private QuestionService questionService;

    @Resource
    private QuestionESDAO questionESDAO;

    @Override
    public void run(String... args) {
        // 1) 从数据库全量获取题目
        // List<Question> questionList = questionService.list();
        List<Question> questionList = new ArrayList<>();
        if (CollUtil.isEmpty(questionList)) {
            return;
        }

        // 2) 转为 ES 实体类
        List<QuestionESDTO> questionEsDTOList = questionList.stream()
                .map(QuestionESDTO::objToDto)
                .collect(Collectors.toList());

        // 3) 分页批量插入到 ES
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("FullSyncQuestionToEs start, total {}", total);

        for (int i = 0; i < total; i += pageSize) {
            // 注意同步的数据下标不能超过总数据量
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            questionESDAO.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("FullSyncQuestionToEs end, total {}", total);
    }
}
