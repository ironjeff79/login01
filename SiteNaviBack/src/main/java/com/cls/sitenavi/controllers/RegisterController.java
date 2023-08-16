package com.cls.sitenavi.controllers;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.IRegisterService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    public IRegisterService registerService;

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
        //ユーザー名とメールが重複するかどうかの判断
        List<Map<String, Object>> list = registerService.getUserId(user.getUserId());
        List<Map<String, Object>> list2 = registerService.getMail(user.getMail());
        Message msg = new Message();
        if (list.size() > 0) {
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
            registerService.updateUserInfo(user.getUserId(),user.getMail(),user.getPhoneNum(),user.getPassword());
            msg.setCode("success");
            msg.setMsg("ユーザーが登録しました。");
            String msg1 = JSON.toJSONString(msg);
            return msg1;
        }
    }
}