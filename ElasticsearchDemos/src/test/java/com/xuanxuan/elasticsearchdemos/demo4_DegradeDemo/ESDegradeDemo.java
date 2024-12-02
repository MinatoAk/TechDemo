package com.xuanxuan.elasticsearchdemos.demo4_DegradeDemo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ESDegradeDemo {

    /**
     * 检测 ES 是否正常连接
     * 如果 ES 非正常连接则请求数据库做一个数据降级
     */
    @Test
    public void test() {
        if (checkElasticsearch()) {
            System.out.println("[x] ElasticSearch is available");

        } else {
            System.out.println("[x] ElasticSearch is not available, falling back to database");
            // 查询数据库做一个数据降级
        }
    }

    /**
     * 向 ES 发送 INFO 请求以验证 ES 是否已经开启
     *
     * @return ES 链接状态
     */
    private boolean checkElasticsearch() {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")))) {
            // 发送 INFO 请求，如果成功则说明 ES 成功连接
            MainResponse response = client.info(RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            System.err.println("Error connecting to Elasticsearch: " + e.getMessage());
            return false;
        }
    }
}

