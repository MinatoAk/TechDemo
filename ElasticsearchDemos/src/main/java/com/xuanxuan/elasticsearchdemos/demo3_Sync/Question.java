package com.xuanxuan.elasticsearchdemos.demo3_Sync;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// @TableName(value ="question")
@Data
public class Question implements Serializable {
    private Long id;
    private String title;
    private String answer;
    private String tags; // JSON 格式存储
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
