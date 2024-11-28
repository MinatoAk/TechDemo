package com.xuanxuan.elasticsearchdemos.demo1_CRUD;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class demo1_ESDAO_CRUD {

    @Resource
    private PostEsDAO postEsDAO;

    /**
     * 增, 改
     */
    @Test
    public void testAdd2ES() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(3L);
        postEsDTO.setTitle("轩轩不想熬大夜");
        postEsDTO.setContent("demo 学习来源于编程导航，欢迎大家一起学习，顺便给我的仓库一个 star");
        postEsDTO.setTags(Arrays.asList("轩轩", "熬夜"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);

        postEsDAO.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    /**
     * 查
     */
    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDAO.findById(1L);
        System.out.println(postEsDTO);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        List<PostEsDTO> postEsDTOS = postEsDAO.findByUserId(userId);
        System.out.println(postEsDTOS.size());
        System.out.println(postEsDTOS);
    }

    @Test
    void testFindByTitle() {
        // 这边可以改标题，试试 ES 的分词
        String title = "你好";
        List<PostEsDTO> postEsDTOS = postEsDAO.findByTitle(title);
        System.out.println(postEsDTOS.size());
        System.out.println(postEsDTOS);
    }

    /**
     * 分页查询
     */
    @Test
    void testPageSelect() {
        System.out.println(postEsDAO.count());
        Page<PostEsDTO> PostPage = postEsDAO.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));

        List<PostEsDTO> postList = PostPage.getContent();
        System.out.println(postList);
    }

    /**
     * 删
     */
    @Test
    void testDeleteById() {
        Long postId = 3L;
        postEsDAO.deleteById(postId);
    }
}
