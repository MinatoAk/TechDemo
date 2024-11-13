package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

public interface PostService {

    /**
     * 一般都会传入前端参数，返回实体类，这边做了个简化
     * Page<Post> searchFromEs(PostQueryRequest postQueryRequest);
     */
    void searchFromEs();
}
