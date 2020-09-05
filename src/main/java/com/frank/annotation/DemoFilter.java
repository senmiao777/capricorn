package com.frank.annotation;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2020/9/5 0005 上午 10:33
 */

@WebFilter(filterName = "EncrypteFilter", urlPatterns = "/*")
@Configuration
public class DemoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("-----------DemoFilter init---------");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------DemoFilter doFilter------------");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Object stockCode = request.getParameter("stockCode");
        Map<String, String[]> params = request.getParameterMap();

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String key = param.getKey();  // 参数名
            String[] value = param.getValue();  // 参数值
            System.out.println("key="+ key+"value="+value);

        }

        if (stockCode != null) {
            int i = 1100;
            System.out.println("url="+ request.getRequestURL());
            request.setAttribute("userId", i);

        } else {
            System.out.println("stockCode is null");
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        System.out.println("---------DemoFilter destroy----------");
    }
}
