package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class demo1_ESRestTemplateTest {

    @Resource
    private PostService postService;

    @Test
    public void test() {
        postService.searchFromEs();
    }
}
