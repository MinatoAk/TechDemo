package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 一般都会传入前端参数，返回实体类，这边做了个简化
     * Page<Post> searchFromEs(PostQueryRequest postQueryRequest);
     *
     * 整个流程如下:
     * 1. 先在 Kibana 写 DSL 并测试
     * 2. 对着 DSL 写 ElasticsearchRestTemplate 查询代码
     * 3. 拿着查询出来的结果去查数据库
     * 4. 返回数据库查询出来的完整数据
     *
     * QA:
     * 1. 为什么还要再查数据库
     * 因为 ES 里面存的一般都是静态字段，不会经常更新，比如点赞数就不会存在 ES 里面，所以需要再查数据库
     * ES 保证存放的字段尽量精简，只包含用户经常查询，并且不会经常更新的字段
     */
    @Override
    public void searchFromEs() {
        // 1. 从前端传入的参数中拿出对应参数
        Long id = 1L;
        Long notId = null;
        String searchText = null;
        String title = null;
        String content = null;
        List<String> tagList = null;
        List<String> orTagList = null;
        Long userId = null;
        long current = 0;
        long pageSize = 5;
        String sortField = null;
        String sortOrder = null;

        // 2. 根据 DSL 构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.filter(QueryBuilders.termQuery("isDelete", 0));
        if (id != null) boolQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        if (notId != null) boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", notId));
        if (userId != null) boolQueryBuilder.filter(QueryBuilders.termQuery("userId", userId));

        // 必须包含所有标签
        if (CollectionUtils.isNotEmpty(tagList)) {
            for (String tag : tagList) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("tags", tag));
            }
        }

        // 包含任何一个标签即可
        if (CollectionUtils.isNotEmpty(orTagList)) {
            BoolQueryBuilder orTagBoolQueryBuilder = QueryBuilders.boolQuery();
            for (String tag : orTagList) {
                orTagBoolQueryBuilder.should(QueryBuilders.termQuery("tags", tag));
            }
            orTagBoolQueryBuilder.minimumShouldMatch(1);
            boolQueryBuilder.filter(orTagBoolQueryBuilder);
        }

        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }

        // 按标题检索
        if (StringUtils.isNotBlank(title)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", title));
            boolQueryBuilder.minimumShouldMatch(1);
        }

        // 按内容检索
        if (StringUtils.isNotBlank(content)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("content", content));
            boolQueryBuilder.minimumShouldMatch(1);
        }

        // 3. 排序条件
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order("ascend".equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 4. 分页条件
        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);

        // 5. 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withSorts(sortBuilder).build();
        SearchHits<PostEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, PostEsDTO.class);

        // 6. 查询数据库获取完整结果，这里直接做了个简化
        // 如果数据库查询不到数据，应该在 ES 删除对应记录
        // -> String delete = elasticsearchRestTemplate.delete(String.valueOf(postId), PostEsDTO.class);
        if (searchHits.hasSearchHits()) {
            long totalHits = searchHits.getTotalHits();
            List<SearchHit<PostEsDTO>> searchHitList = searchHits.getSearchHits();
            List<Long> postIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());

            System.out.println("totalHits: " + totalHits);
            System.out.println("postIdList: " + postIdList);
        }
    }
}
