package com.frank.interceptor;

import com.frank.annotation.Encrypt;
import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author frank
 * @version 1.0
 * @date 2018/8/25 0025 下午 9:56
 * 记录请求路径，处理耗时
 */
@Slf4j
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(Common.REQUEST_AT.getValue(), System.currentTimeMillis());
        MDC.put(Common.TRACING_ID.getValue(), String.valueOf(RandomUtils.nextInt(100000000, 999999999)));
        log.info("请求开始url={},IP={}", request.getServletPath(), Objects.toString(request.getHeader(Common.REAL_IP.getValue()), Common.DEFAULT_IP.getValue()));

        if(handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            boolean b = h.hasMethodAnnotation(Encrypt.class);
            if(b){
                System.out.println("---------方法："+request.getRequestURI() + "加了Encrypt注解");
            }else {
                System.out.println("---------方法："+request.getRequestURI() + "没有Encrypt注解");
            }
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            log.info("modelAndView={}", modelAndView.toString());
            Map<String, Object> modelMap = modelAndView.getModelMap();
            log.info("modelMap={},isEmpty={}", modelMap.toString(), modelMap.isEmpty());
        } else {
            log.info("modelAndView is null");

        }

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            log.info("请求结束url={},耗时{}ms", request.getServletPath(), System.currentTimeMillis() - Long.valueOf(Objects.toString(request.getAttribute(Common.REQUEST_AT.getValue()), "0")));
        } catch (NumberFormatException e) {
        } finally {
            MDC.remove(Common.TRACING_ID.getValue());
        }
        super.afterCompletion(request, response, handler, ex);
    }
}
