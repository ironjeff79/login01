package com.cls.sitenavi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cls.common.util.Util;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.entity.Web;
import com.cls.sitenavi.service.IWebService;

@Service
public class WebServiceImpl implements IWebService {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public Map<String,Object> getWebInfo(int a) {
		String sql = "select web_title from db_web left join db_webinfo on db_web.web_id = db_webinfo.web_id order by click_count desc limit " + a + "";
		String sql2 ="select click_count from db_webinfo order by click_count desc limit " + a + "";
		List<Web> webList = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<Web>(Web.class));
		List<String> web_id = new ArrayList<>();
		 webList.stream().forEach(item->{
			 web_id.add(item.getWeb_title());
        });
		
		List<Web> webList2 = jdbcTemplate.query(sql2, new Object[] {}, new BeanPropertyRowMapper<Web>(Web.class));
		List<String> click_count = new ArrayList<>();
		 webList2.stream().forEach(item->{
			 click_count.add(item.getClick_count());
       });
				
		Map<String, Object> res = new HashMap<>();

		res.put("web_id", web_id);
		res.put("click_count", click_count);
		return res;
	}

	public User confirmUserInfo(User user) {
		String pass = Util.code(user.getPassword());
		String sql = "select * from user where userId = '" + user.getUserId() + "' and password ='" + pass + "'";
		User user1 = new User();
		List<User> userList = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
		if (null != userList && userList.size() > 0) {
			user1 = userList.get(0);
			return user1;
		}
		return null;
	}



	
}
