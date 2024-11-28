package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * ES DTO
 * 这边已经通过 Kibana 控制台 PUT 过了一个帖子的索引
 * 具体的建立索引 Mapping 的语句放在 markdown 文件中了
 */

// 指定和哪个索引绑定
@Document(indexName = "post")
@Data
public class PostEsDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Id
    private Long id;

    private String title;

    private String content;

    private List<String> tags;

    private Long userId;

    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date createTime;

    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updateTime;

    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}