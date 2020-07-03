package com.nervenets.general.filter;

import com.nervenets.general.wapper.NerveNetsHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 2020/6/23 17:49 created by Joe
 **/
public class NerveNetsRequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if ("application/x-www-form-urlencoded".equals(servletRequest.getHeader("content-type"))) {
            filterChain.doFilter(servletRequest, response);
        } else {
            //防止后续参数能正常获取，需要先读取一次...
            servletRequest.getParameterMap();
            servletRequest.getParameterNames();

            NerveNetsHttpServletRequestWrapper wrapper = new NerveNetsHttpServletRequestWrapper((HttpServletRequest) request);
            filterChain.doFilter(wrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
