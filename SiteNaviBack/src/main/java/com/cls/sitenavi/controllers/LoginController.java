package com.cls.sitenavi.controllers;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.ILoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
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
        User list = loginService.getUserInfo(user);
        Message msg = new Message();

        if (list != null) {
            msg.setCode("success");
            msg.setMsg("成功しました！");
            HttpSession session = req.getSession();
            session.setAttribute("user",list);
            User user1 = (User) session.getAttribute("user");
            System.out.println("user1");
            System.out.println(user1);
            msg.setUser(list);
            session.setMaxInactiveInterval(60);
            return JSON.toJSONString(msg);
        } else {
            msg.setCode("warning");
            msg.setMsg("正しいIDとパスワードを入力してください");

            return JSON.toJSONString(msg);
        }
    }
    @PostMapping("/portal")
    public String portal(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 获取JSON数据
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
        User list = loginService.getMail(user);
        return JSON.toJSONString(list);
    }
}
