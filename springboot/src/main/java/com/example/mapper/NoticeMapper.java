package com.example.mapper;

import com.example.entity.Notice;
import java.util.List;

/**
 * 公告信息表-Mapper方法接口
 */
public interface NoticeMapper {

    /**
     * 01. 查询所有公告
     *
     * @param notice 用于条件查询的公告实体
     * @return 公告列表
     */
    List<Notice> selectAll(Notice notice);

    /**
     * 02. 新增公告
     *
     * @param notice 要新增的公告实体
     * @return 受影响的行数
     */
    int insert(Notice notice);

    /**
     * 03. 修改公告
     *
     * @param notice 要修改的公告实体
     * @return 受影响的行数
     */
    int updateById(Notice notice);

    /**
     * 04. 删除公告
     *
     * @param id 要删除的公告ID
     * @return 受影响的行数
     */
    int deleteById(Integer id);

    /**
     * 05. 批量删除公告
     *
     * @param ids 要删除的公告ID列表
     * @return 受影响的行数
     */
    int deleteBatch(List<Integer> ids);

    /**
     * 06. 通过ID查询公告
     *
     * @param id 公告ID
     * @return 对应的公告实体
     */
    Notice selectById(Integer id);

    /**
     * 07. 分页查询公告
     *
     * @param notice 用于条件查询的公告实体
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 分页后的公告列表
     */
    List<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);
}
