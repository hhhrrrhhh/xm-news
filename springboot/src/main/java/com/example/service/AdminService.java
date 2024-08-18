package com.example.service;

import com.example.common.enums.RoleEnum;
import com.example.entity.Admin;
import com.example.mapper.AdminMapper;
import com.example.common.enums.ResultCodeEnum;
import com.example.exception.CustomException;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员业务处理
 **/
@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 01. 查询所有管理员
     *
     * @param admin 用于条件查询的管理员实体
     * @return 管理员列表
     */
    public List<Admin> selectAll(Admin admin) {
        return adminMapper.selectAll(admin);
    }

    /**
     * 02. 新增管理员
     *
     * @param admin 要新增的管理员实体
     */
    public void add(Admin admin) {
        // 添加前检查用户名是否已经存在
        Admin existingAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (existingAdmin != null) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        adminMapper.insert(admin);
    }

    /**
     * 03. 修改管理员信息
     *
     * @param admin 要修改的管理员实体
     */
    public void updateById(Admin admin) {
        adminMapper.updateById(admin);
    }

    /**
     * 04. 删除管理员
     *
     * @param id 要删除的管理员ID
     */
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    /**
     * 05. 批量删除管理员
     *
     * @param ids 要删除的管理员ID列表
     */
    public void deleteBatch(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            adminMapper.deleteBatch(ids);
        }
    }

    /**
     * 06. 通过ID查询管理员
     *
     * @param id 管理员ID
     * @return 对应的管理员实体
     */
    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    /**
     * 07. 分页查询管理员
     *
     * @param admin    用于条件查询的管理员实体
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     * @return 分页后的管理员列表
     */
    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    /**
     * 08. 管理员登录
     *
     * @param admin 登录信息
     * @return 登录后的管理员信息
     */
    public Admin login(Admin admin) {
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (dbAdmin == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!admin.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        String token = TokenUtils.createToken(dbAdmin.getId() + "-" + RoleEnum.ADMIN.name(), dbAdmin.getPassword());
        dbAdmin.setToken(token);
        return dbAdmin;
    }

    /**
     * 09. 管理员注册
     *
     * @param admin 注册信息
     */
    public void register(Admin admin) {
        if (adminMapper.selectByUsername(admin.getUsername()) != null) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        adminMapper.insert(admin);
    }

    /**
     * 10. 更新管理员密码
     *
     * @param admin 更新信息
     */
    public void updatePassword(Admin admin) {
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (dbAdmin == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!admin.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbAdmin.setPassword(admin.getNewPassword());
        adminMapper.updateById(dbAdmin);
    }
}
