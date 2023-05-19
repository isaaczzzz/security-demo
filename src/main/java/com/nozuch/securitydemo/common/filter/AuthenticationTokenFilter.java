package com.nozuch.securitydemo.common.filter;

import com.nozuch.securitydemo.common.constant.SecurityConst;
import com.nozuch.securitydemo.common.domain.LoginUser;
import com.nozuch.securitydemo.common.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String idToken = request.getHeader(SecurityConst.TOKEN_HEADER);
        if (!StringUtils.hasText(idToken)) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token，获取下划线之前的部分（id）
        String id;
        String token;
        int i = idToken.indexOf('_');
        if (i > 0) {
            id = idToken.substring(0, i);
            token = idToken.substring(i + 1);
        } else {
            throw new RuntimeException("token格式非法");
        }
        // 从redis获取token并验证
        LoginUser loginUser = redisCache.getCacheObject(SecurityConst.TOKEN_PREFIX + id);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录或token已过期");
        } else if (!token.equals(loginUser.getToken())) {
            throw new RuntimeException("token错误");
        }
        // 存入SecurityContextHolder，并存入响应权限信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
