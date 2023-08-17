package com.cls.sitenavi.service.impl;
import com.cls.common.util.Util;
import  com.cls.sitenavi.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public List<Map<String, Object>> getUserInfo(String userId,String password){
        String pass= Util.code(password) ;
        String sql = "select * from user where userId = '"  + userId + "' and password ='" + pass + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
}


