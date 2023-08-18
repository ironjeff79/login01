package com.cls.sitenavi.service.impl;
import com.cls.common.util.Util;
import com.cls.sitenavi.entity.User;
import  com.cls.sitenavi.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public User getUserInfo(User user){
        String pass= Util.code(user.getPassword()) ;
        String sql = "select * from user where userId = '"  + user.getUserId() + "' and password ='" + pass + "'";
        User user1 = new User();
        List<User> userList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<User>(User.class));
        if(null!= userList && userList.size()>0){
            user1 = userList.get(0);
            return user1;
        }
        return null;
    }
    public User getMail(User user) {
        String sql = "select * from user where userId = '" + user.getUserId() + "'";
        User user1 = new User();
        List<User> userList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<User>(User.class));
        if (null != userList && userList.size() > 0) {
            user1 = userList.get(0);
            return user1;
        }
        return null;
    }
}


