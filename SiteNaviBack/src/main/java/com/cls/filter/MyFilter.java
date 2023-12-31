package com.cls.filter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cls.sitenavi.entity.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:8081")
@Slf4j
@WebFilter(filterName = "authFilter", urlPatterns = "/*")
@Order(1) //顺序
//@Component
public class MyFilter implements Filter {
    @Autowired
	public JdbcTemplate jdbcTemplate;
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.debug("start to auth request validate...111");
    log.info("enter doFilter");

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    String url = req.getRequestURI();
    System.out.println("url");
    System.out.println(url);
      // 允许的请求方法
      res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
      // 允许的请求头
      res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
      // 设置预检请求的有效期，单位为秒
      res.setHeader("Access-Control-Max-Age", "3600");
      // 如果是 OPTIONS 请求，直接返回成功状态码
      if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
          res.setStatus(HttpServletResponse.SC_OK);
          System.out.println("进入OPTIONS方法");
          return;
      }
      System.out.println("离开OPTIONS方法");

    String token = req.getHeader("Token");
    System.out.println("收到的token");
    System.out.println(token);
    log.info("获取到的token为：{}", token);
    String sql = "select * from user where token = '" + token + "'";
	User user1 = new User();
	List<User> userList = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
	System.out.println("userList");
	System.out.println(userList);
	 if (url.endsWith("/login")||url.endsWith("/logOut")) {
		 chain.doFilter(request, response);
         log.info("login logout success");
	 }
	 else if (null != userList && userList.size() > 0 ) {
	        chain.doFilter(request, response);
	        log.info("auth success");
	      } else {
//	    	  res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	  log.info("auth failed");
	        
//	        res.sendRedirect(req.getContextPath() + "/login");
	      }	
	}

    
    
// 4.如果令牌为空，则显示没有登录
//    if (StringUtils.isEmpty(token)) {
//      Message responseResult = new Message<>();
//      responseResult.setMsg("没有登录！");
//      response.setContentType("application/json;charset=utf-8");
//      response.getWriter().write(JSONObject.toJSONString(responseResult));
//      return;
//    }
//    HttpServletResponse res = (HttpServletResponse) response;
//    HttpSession session = req.getSession();
//    String url = req.getRequestURI().substring(req.getContextPath().length());


//    if (token != "0") {
//      //    :TODO check token
//      chain.doFilter(request, response);
//      log.info("auth success");
//    } else {
//      log.info("auth failed");
//      res.sendRedirect(req.getContextPath() + "/login");
//    }
  }
