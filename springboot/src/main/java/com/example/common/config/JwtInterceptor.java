package com.example.common.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 允许未登录访问 /front/* 下的所有路径
        if (requestURI.startsWith("/front")) {
            return true; // 直接放行
        }

        // 只拦截 /manager/* 下的路径
        if (requestURI.startsWith("/manager")) {
            log.info("Intercepting request for URI: " + requestURI);

            // 1. 从http请求的header中获取token
            String token = request.getHeader(Constants.TOKEN);
            if (ObjectUtil.isEmpty(token)) {
                token = request.getParameter(Constants.TOKEN);
            }
            if (ObjectUtil.isEmpty(token)) {
                log.warn("Token not found in request");
                response.sendRedirect("/login");
                return false;
            }

            Admin admin = null;
            try {
                String userRole = JWT.decode(token).getAudience().get(0);
                String userId = userRole.split("-")[0];
                String role = userRole.split("-")[1];
                log.info("Decoded Token - UserId: " + userId + ", Role: " + role);

                if (RoleEnum.ADMIN.name().equals(role)) {
                    admin = adminService.selectById(Integer.valueOf(userId));
                    log.info("Admin fetched from database: " + admin);
                }
            } catch (Exception e) {
                log.error("Error decoding token", e);
                response.sendRedirect("/login");
                return false;
            }

            if (ObjectUtil.isNull(admin)) {
                log.warn("Admin not found for token");
                response.sendRedirect("/login");
                return false;
            }

            try {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
                jwtVerifier.verify(token); // 验证token
                log.info("Token verified successfully for admin: " + admin.getUsername());
            } catch (JWTVerificationException e) {
                log.error("Token verification failed", e);
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }
}

