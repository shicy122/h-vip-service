package com.hycan.idn.hvip.filter;

import com.hycan.idn.hvip.constants.HttpConstants;
import com.hycan.idn.hvip.pojo.vip.LoginUserDTO;
import com.hycan.idn.hvip.util.LoginUserUtils;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息过滤器，将用户信息暂存到本地线程变量中，执行完成过滤器则清除用户信息
 *
 * @author shichongying
 * @datetime 2023-10-21 10:54
 */
@Slf4j
@WebFilter(filterName = "loginUserFilter", urlPatterns = {"/vip-svc/*"})
public class LoginUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userId = request.getHeader(HttpConstants.H_USER_ID);
        String userName = request.getHeader(HttpConstants.H_USER_NAME);

        if (StringUtils.isNotBlank(userId)) {
            LoginUserUtils.setLoginUser(LoginUserDTO.of(Long.parseLong(userId), userName));
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable t) {
            log.error("执行获取登录用户过滤器错误, 异常详情={}", ExceptionUtil.getBriefStackTrace(t));
        } finally {
            LoginUserUtils.removeUser();
        }
    }
}
