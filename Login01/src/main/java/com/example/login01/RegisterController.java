package com.example.login01;

import com.alibaba.fastjson.JSON;
import com.example.login01.domain.Message;
import com.example.login01.domain.User;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
@RestController
public class RegisterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RegisterController.class, args);
    }


    @GetMapping("/register")
    public String register() {
        return String.format("111");
    }

    @PostMapping("/register")
    public String getJson2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取JSON数据
        ServletInputStream is = req.getInputStream();
        byte[] buffer = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (is.read(buffer) != -1) {
            sb.append(new String(buffer, "utf-8"));
        }
        String json = sb.toString().trim();
        User user = JSON.parseObject(json, User.class);


        System.out.println("newuser");
        System.out.println(user);
        String sql1 = "select * from user where userId = '" + user.getUserId() + "'";
        String sql2 = "select * from user where mail = '" + user.getMail() + "'";
//        String sql2 = "select * from user where userId = '"  + user.getUserId() + "' or mail='" + user.getMail() + "'";
        List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql1);
        List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2);
        Message msg = new Message();
        if (list1.size() > 0) {
            msg.setCode("warning");
            msg.setMsg("ユーザー名が重複しています");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        } else if (list2.size() > 0) {
            msg.setCode("warning");
            msg.setMsg("メールアドレスはすでに使用されています");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        } else {
            String sql3 = "insert into user (userId,mail,phoneNum,password) values ('" + user.getUserId() + "'" + ",'" + user.getMail() + "'" + ",'" + user.getPhoneNum() + "'" + ",'" + user.getPassword() + "')";
            jdbcTemplate.execute(sql3);
            msg.setCode("success");
            msg.setMsg("ユーザーが登録しました。");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        }

    }
}