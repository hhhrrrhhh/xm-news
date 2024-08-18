package com.example.mapper;

import com.example.entity.Provinces;

import java.util.List;

/**
 * 省份-Mapper方法接口
 */
public interface ProvincesMapper {

    /**
     * 01. 查询所有省份
     *
     * @param provinces 用于条件查询的省份实体
     * @return 省份列表
     */
    List<Provinces> selectAll(Provinces provinces);

    /**
     * 02. 新增省份
     *
     * @param provinces 要新增的省份实体
     * @return 受影响的行数
     */
    int insert(Provinces provinces);

    /**
     * 03. 修改省份信息
     *
     * @param provinces 要修改的省份实体
     * @return 受影响的行数
     */
    int updateById(Provinces provinces);

    /**
     * 04. 删除省份信息
     *
     * @param id 要删除的省份ID
     * @return 受影响的行数
     */
    int deleteById(Integer id);

    /**
     * 05. 批量删除省份信息
     *
     * @param ids 要删除的省份ID列表
     * @return 受影响的行数
     */
    int deleteBatch(List<Integer> ids);

    /**
     * 06. 通过ID查询省份
     *
     * @param id 省份ID
     * @return 对应的省份实体
     */
    Provinces selectById(Integer id);

    /**
     * 07. 分页查询省份
     *
     * @param provinces 用于条件查询的省份实体
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 分页后的省份列表
     */
    List<Provinces> selectPage(Provinces provinces, Integer pageNum, Integer pageSize);
}
