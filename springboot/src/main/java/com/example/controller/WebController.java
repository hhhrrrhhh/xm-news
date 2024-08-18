package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Admin;
import com.example.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Web控制器，处理登录、注册和更新密码等请求
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    /**
     * 01. 默认的欢迎页面
     *
     * @return 访问成功信息
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 02. 管理员登录
     *
     * @param admin 包含登录信息的管理员实体
     * @return 登录结果和管理员信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        if (ObjectUtil.isEmpty(admin.getUsername()) || ObjectUtil.isEmpty(admin.getPassword())
                || ObjectUtil.isEmpty(admin.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(admin.getRole())) {
            admin = adminService.login(admin);
        }
        return Result.success(admin);
    }

    /**
     * 03. 管理员注册
     *
     * @param admin 包含注册信息的管理员实体
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        if (StrUtil.isBlank(admin.getUsername()) || StrUtil.isBlank(admin.getPassword())
                || ObjectUtil.isEmpty(admin.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(admin.getRole())) {
            adminService.register(admin);
        }
        return Result.success();
    }

    /**
     * 04. 管理员更新密码
     *
     * @param admin 包含更新信息的管理员实体
     * @return 更新结果
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Admin admin) {
        if (StrUtil.isBlank(admin.getUsername()) || StrUtil.isBlank(admin.getPassword())
                || ObjectUtil.isEmpty(admin.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(admin.getRole())) {
            adminService.updatePassword(admin);
        }
        return Result.success();
    }
}
