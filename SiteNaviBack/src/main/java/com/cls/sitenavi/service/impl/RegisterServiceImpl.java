package com.cls.sitenavi.service.impl;
import com.cls.common.util.Util;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public List<Map<String, Object>> getUserId(String userId){
        String sql = "select * from user where userId = '" + userId + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    public List<Map<String, Object>> getMail(String mail){
        String sql = "select * from user where mail = '" + mail + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    public void insertUserInfo(User user){
        String pass= Util.code(user.getPassword()) ;
        String sql = "insert into user (userId,mail,phoneNum,password) values ('" + user.getUserId() + "'"
                + ",'" + user.getMail() + "'" + ",'" + user.getPhoneNum() + "'" + ",'" + pass + "')";
        jdbcTemplate.execute(sql);
    }
}
