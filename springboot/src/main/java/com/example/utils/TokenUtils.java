package com.example.utils;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.Constants;
import com.example.entity.Admin;
import com.example.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 */
@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static AdminService staticAdminService;

    @Resource
    private AdminService adminService;

    @PostConstruct
    public void setAdminService() {
        staticAdminService = adminService;
    }

    /**
     * 01. 生成Token
     *
     * @param data 保存到Token中的数据
     * @param sign 用于加密的密钥
     * @return 生成的Token字符串
     */
    public static String createToken(String data, String sign) {
        return JWT.create()
                .withAudience(data) // 将管理员ID-role保存到Token里面,作为载荷
                .sign(Algorithm.HMAC256(sign)); // 以密码作为Token的密钥
    }

    /**
     * 02. 获取当前登录的管理员信息
     *
     * @return 当前管理员的信息
     */
    public static Admin getCurrentAdmin() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {
                String userRole = JWT.decode(token).getAudience().get(0);
                String userId = userRole.split("-")[0];  // 获取管理员ID
                return staticAdminService.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            log.error("获取当前管理员信息出错。可能原因：Token解析失败或管理员ID无效", e);
        }
        return null;  // 未能获取到管理员信息时返回null
    }
}
