package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

// 继承 ElasticsearchRepository 前面是索引的 DTO 的类型，后面是 id 的类型

public interface PostEsDAO extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);

    List<PostEsDTO> findByTitle(String title);
}