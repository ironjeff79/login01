package com.cls.sitenavi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cls.common.util.Util;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	//    public List<User> getVagueUserInfo(User user){
	//        String sql = "select * from user where userId like '%"  + user.getUserId() +"%'";
	//        User user1 = new User();
	//        List<User> userList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<User>(User.class));
	//        System.out.println(userList);
	//        return  userList;
	//    }
	public User getDirectUserInfo(User user) {
		String sql = "select * from user where id = '" + user.getId() + "'";
		User user1 = new User();
		List<User> userList = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
		if (null != userList && userList.size() > 0) {
			user1 = userList.get(0);
			return user1;
		}
		return null;
	}
	
	public void saveToken(User user,String token) {
		String sql = "UPDATE user SET token = '" + token + "'  WHERE userId ='" + user.getUserId() + "'";
		 jdbcTemplate.execute(sql);
	}
	public void deleteToken(User user,String token) {
		System.out.println("deleteToken");
		System.out.println(user);
		String sql = "UPDATE user SET token = null WHERE userId ='" + user.getUserId() + "'";
		 jdbcTemplate.execute(sql);
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

	public User getMail(User user) {
		String sql = "select * from user where userId = '" + user.getUserId() + "'";
		User user1 = new User();
		List<User> userList = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
		if (null != userList && userList.size() > 0) {
			user1 = userList.get(0);
			return user1;
		}
		return null;
	}

	public Map<String, Object> searchPage(Map maps) {

		String sqll = "select * from user where userId like '%" + maps.get("userId") + "%'";
		List<User> userList = jdbcTemplate.query(sqll, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
		Integer pageTotal = 0;
		Integer pageSize = (Integer) maps.get("pageSize");
		if (userList.size() % pageSize != 0) {
			pageTotal = userList.size() / pageSize + 1;
		} else {
			pageTotal = userList.size() / pageSize;
		}
		Object a = ((Integer) maps.get("page") - 1) * pageSize;
		String sql = "select * from user where userId like '%" + maps.get("userId")
				+ "%'and id >= (select id from user order by id limit " + a + ", 1) limit " + pageSize + "";
		User user1 = new User();
		List<User> userList1 = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));

		Map<String, Object> res = new HashMap<>();

		res.put("totalPage", pageTotal);
		res.put("userList", userList1);
		return res;
	}
}
