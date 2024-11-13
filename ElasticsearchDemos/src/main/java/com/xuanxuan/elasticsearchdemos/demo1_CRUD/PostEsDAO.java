package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostEsDAO extends ElasticsearchRepository<PostEsDTO, Long> {

    List<PostEsDTO> findByUserId(Long userId);

    List<PostEsDTO> findByTitle(String title);
}