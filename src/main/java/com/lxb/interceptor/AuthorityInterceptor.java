package com.lxb.interceptor;

import com.lxb.example.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-06-02 14:56
 **/
public class AuthorityInterceptor extends  HandlerInterceptorAdapter{
    private static final Logger log = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        log.info("--------preHandle-------,contetxPath:{},url:{},method:{}", request.getContextPath(), request.getRequestURI(), request.getMethod());
        return  true;

    }

}
