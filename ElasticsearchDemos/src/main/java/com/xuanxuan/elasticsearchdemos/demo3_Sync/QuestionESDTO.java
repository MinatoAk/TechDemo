package com.xuanxuan.elasticsearchdemos.demo3_Sync;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// @Document(indexName = "question")
@Data
public class QuestionESDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Id
    private Long id;

    private String title;

    private String answer;

    private List<String> tags;

    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date updateTime;


    private static final long serialVersionUID = 1L;

    public static QuestionESDTO objToDto(Question question) {
        if (question == null) {
            return null;
        }
        QuestionESDTO questionEsDTO = new QuestionESDTO();
        BeanUtils.copyProperties(question, questionEsDTO);
        String tagsStr = question.getTags();
        if (StringUtils.isNotBlank(tagsStr)) {
            questionEsDTO.setTags(JSONUtil.toList(tagsStr, String.class));
        }
        return questionEsDTO;
    }
}
