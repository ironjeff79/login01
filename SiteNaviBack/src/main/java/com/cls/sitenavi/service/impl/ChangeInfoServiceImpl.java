package com.cls.sitenavi.service.impl;
import com.cls.common.util.Util;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.IChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChangeInfoServiceImpl implements IChangeService {

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Override
    public List<Map<String, Object>> changeUserInfo(String userId,String newUserId,String password){
        String pass= Util.code(password) ;
        String sql = "select * from user where userId = '"  + userId + "' and password ='" + pass + "'";
       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    public void changePass(String userId,String password){
        String pass= Util.code(password) ;
        String sql = "update user set password = '"  + pass + "' where userId ='" + userId + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        jdbcTemplate.execute(sql);
    }
    public void changeUserInfo(User user){
        String pass= Util.code(user.getPassword()) ;
        String sql = "update user set password = '"  + pass +"',userId = '"  + user.getNewUserId() + "' where userId ='" + user.getUserId() + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        jdbcTemplate.execute(sql);
    }
}
