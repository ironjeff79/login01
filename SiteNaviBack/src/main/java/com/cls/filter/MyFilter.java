package com.cls.filter;
import java.io.IOException;


import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.entity.User;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@WebFilter(filterName = "authFilter", urlPatterns = "/*")
@Order(1) //顺序
public class MyFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.debug("start to auth request validate...111");
    log.info("enter doFilter");

    HttpServletRequest req = (HttpServletRequest) request;
    String a = req.getRequestURI();
    System.out.println("getRequestURI");
    System.out.println(a);

    String token = req.getHeader("Token");
    log.info("获取到的token为：{}", token);
    // 4.如果令牌为空，则显示没有登录
//    if (StringUtils.isEmpty(token)) {
//      Message responseResult = new Message<>();
//      responseResult.setMsg("没有登录！");
//      response.setContentType("application/json;charset=utf-8");
//      response.getWriter().write(JSONObject.toJSONString(responseResult));
//      return;
//    }
    HttpServletResponse res = (HttpServletResponse) response;
//    HttpSession session = req.getSession();
//    String url = req.getRequestURI().substring(req.getContextPath().length());


    if (token != "0") {
      //    :TODO check token
      chain.doFilter(request, response);
      log.info("auth success");
    } else {
      log.info("auth failed");
      res.sendRedirect(req.getContextPath() + "/login");
    }
    //       session内容check
//      if (session.getAttribute("token") != null) {
//        chain.doFilter(request, response);
//        } else {
//        // session不存在的话，转到login页面
//        res.sendRedirect(req.getContextPath() + "/login");
//      }
//    String token = req.getHeader("token");
//    System.out.println("token");
//    System.out.println(token);
  }
}