package com.cls.sitenavi.service.impl;
import com.cls.common.util.Util;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.IChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChangeInfoServiceImpl implements IChangeService {
    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public User getInfo(User user){
        String sql = "select * from user where mail = '" + user.getMail() + "' and userId <> '" + user.getUserId() + "'" ;
        User user1 = new User();
        List<User> userList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<User>(User.class));
        if (null != userList && userList.size() > 0) {
            user1 = userList.get(0);
            return user1;
        }
        return null;
    }
    public void changeInfo(User user){
    String sql = "update user set mail = '" + user.getMail() + "',password = '" + user.getPassword() +"',phoneNum ='" + user.getPhoneNum() + "' where userId = '" + user.getUserId() + "'";
        jdbcTemplate.execute(sql);
    }
    public void changePass(User user){
        String pass= Util.code(user.getPassword()) ;
        String sql = "update user set mail = '" + user.getMail() + "',password = '" + pass +"',phoneNum ='" + user.getPhoneNum() + "' where userId = '" + user.getUserId() + "'";
        jdbcTemplate.execute(sql);
    }
}
