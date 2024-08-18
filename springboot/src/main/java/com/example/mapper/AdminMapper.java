package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作admin相关数据接口
 */
public interface AdminMapper {

    /**
     * 01. 查询所有管理员
     *
     * @param admin 用于条件查询的管理员实体
     * @return 管理员列表
     */
    List<Admin> selectAll(Admin admin);

    /**
     * 02. 新增管理员
     *
     * @param admin 要新增的管理员实体
     * @return 受影响的行数
     */
    int insert(Admin admin);

    /**
     * 03. 修改管理员信息
     *
     * @param admin 要修改的管理员实体
     * @return 受影响的行数
     */
    int updateById(Admin admin);

    /**
     * 04. 删除管理员
     *
     * @param id 要删除的管理员ID
     * @return 受影响的行数
     */
    int deleteById(Integer id);

    /**
     * 05. 批量删除管理员
     *
     * @param ids 要删除的管理员ID列表
     * @return 受影响的行数
     */
    int deleteBatch(List<Integer> ids);

    /**
     * 06. 通过ID查询管理员
     *
     * @param id 管理员ID
     * @return 对应的管理员实体
     */
    Admin selectById(Integer id);

    /**
     * 07. 分页查询管理员
     *
     * 注：该方法实际通过MyBatis的分页插件在Service层实现，这里是一个标记说明。
     *
     * @param admin 用于条件查询的管理员实体
     * @return 分页后的管理员列表
     */
    // PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);  // 实际上通过PageHelper分页插件实现

    /**
     * 08. 根据用户名查询管理员
     *
     * @param username 管理员用户名
     * @return 对应的管理员实体
     */
    @Select("select * from admin where username = #{username}")
    Admin selectByUsername(String username);
}
