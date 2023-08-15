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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
@RestController
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public static void main(String[] args) {
        SpringApplication.run(LoginController.class, args);
    }

    @GetMapping("/login")
    public String hello1() {
        return String.format("ログインしました");
    }


    @PostMapping("/login")
  public String getJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 获取JSON数据
        ServletInputStream is = req.getInputStream();
        byte[] buffer = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (is.read(buffer) != -1) {
            sb.append(new String(buffer, "utf-8"));
        }
        String json = sb.toString().trim();
        User user = JSON.parseObject(json, User.class);
        System.out.println("user");
        System.out.println(user);
        String sql = "select * from user where userId = '"  + user.getUserId() + "' and password ='" + user.getPassword() + "'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println("list");
        System.out.println(list.size());
        Message msg = new Message();
        if (list.size() > 0 ){
            msg.setCode("success");
            msg.setMsg("成功しました！");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        } else
            msg.setCode("warning");
            msg.setMsg("正しいIDとパスワードを入力してください");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
    }
}

//        public String hello(@RequestParam String userId,String pass ) {
//        System.out.println("ユーザーID" + userId + "，パスワード" + pass);
//        String sql = "select * from user where userId = '" + userId + "' && password = '" + pass + "'";//SQL查询语句
//        System.out.println("sql");
//        System.out.println(sql);
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//        System.out.println("list");
//        System.out.println(list);
//        return userId + pass;}
