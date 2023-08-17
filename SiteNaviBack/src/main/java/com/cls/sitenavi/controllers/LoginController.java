package com.cls.sitenavi.controllers;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.ILoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value="/")
//@WebServlet("/session")
//@RequestMapping("/login")
public class LoginController {
    @Autowired
    public ILoginService loginService;

    @GetMapping("/login")
    public String hello1() {
        return String.format("ログインしました");
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 获取JSON数据
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        User user = JSON.parseObject(responseStrBuilder.toString(), User.class);

        List<Map<String, Object>> list = loginService.getUserInfo(user.getUserId(), user.getPassword());
        Message msg = new Message();
//        HttpSession session = req.getSession();
//        session.setAttribute(user.getUserId(), user.getPassword());
//        session.setMaxInactiveInterval(5);
        if (list.size() > 0) {
            msg.setCode("success");
            msg.setMsg("成功しました！");
            System.out.println("1");
//           javax
            System.out.println("2");
            return JSON.toJSONString(msg);
        } else {
            msg.setCode("warning");
            msg.setMsg("正しいIDとパスワードを入力してください");
            System.out.println("!!");
            return JSON.toJSONString(msg);
        }
    }
}
