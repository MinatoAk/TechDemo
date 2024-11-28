package com.xuanxuan.elasticsearchdemos.demo3_Sync;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionESDAO extends ElasticsearchRepository<QuestionESDTO, Long> {
}
