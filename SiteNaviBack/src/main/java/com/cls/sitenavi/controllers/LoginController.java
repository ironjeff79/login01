package com.cls.sitenavi.controllers;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.ILoginService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
@RestController
public class LoginController {


    @Autowired
    public ILoginService loginService;

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
        List<Map<String, Object>> list = loginService.getUserInfo(user.getUserId(), user.getPassword());

        Message msg = new Message();
        if (list.size() > 0) {
            msg.setCode("success");
            msg.setMsg("成功しました！");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        } else {
            msg.setCode("warning");
            msg.setMsg("正しいIDとパスワードを入力してください");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        }
    }
}
