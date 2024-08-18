package com.example.mapper;

import com.example.entity.News;

import java.util.List;

/**
 * 文章-Mapper方法接口
 */
public interface NewsMapper {

    /**
     * 01. 查询所有文章
     *
     * @param news 用于条件查询的文章实体
     * @return 文章列表
     */
    List<News> selectAll(News news);

    /**
     * 02. 新增一篇文章
     *
     * @param news 要新增的文章实体
     * @return 受影响的行数
     */
    int insert(News news);

    /**
     * 03. 修改一篇文章
     *
     * @param news 要修改的文章实体
     * @return 受影响的行数
     */
    int updateById(News news);

    /**
     * 04. 删除一篇文章
     *
     * @param id 要删除的文章ID
     * @return 受影响的行数
     */
    int deleteById(Integer id);

    /**
     * 05. 批量删除文章
     *
     * @param ids 要删除的文章ID列表
     * @return 受影响的行数
     */
    int deleteBatch(List<Integer> ids);

    /**
     * 06. 通过ID查询文章
     *
     * @param id 文章ID
     * @return 对应的文章实体
     */
    News selectById(Integer id);

    /**
     * 07. 分页查询文章
     *
     * @param news 用于条件查询的文章实体
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 分页后的文章列表
     */
    List<News> selectPage(News news, Integer pageNum, Integer pageSize);
}
