//package com.weilai.log.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//public class TraceIdFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // 初始化方法
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String traceId = httpRequest.getHeader("X-Trace-Id");
//        if (traceId == null || traceId.isEmpty()) {
//            traceId = UUID.randomUUID().toString();
//        }
//        MDC.put("traceId", traceId);
//        try {
//            chain.doFilter(request, response);
//        } finally {
//            MDC.remove("traceId");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        // 销毁方法
//    }
//}
