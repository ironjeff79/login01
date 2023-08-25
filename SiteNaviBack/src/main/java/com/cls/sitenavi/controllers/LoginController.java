package com.cls.sitenavi.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.ILoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        User list = loginService.confirmUserInfo(user);
        Message msg = new Message();
        if (list != null) {
        	if(list.getState().equals("0")) {
           	 msg.setCode("warning");
                msg.setMsg("ユーザーが無効化されました！");
                return JSON.toJSONString(msg);
           }else {
            msg.setCode("success");
            msg.setMsg("成功しました！");
            HttpSession session = req.getSession();
            session.setAttribute("user",list);
            User user1 = (User) session.getAttribute("user");
            msg.setUser(list);
            session.setMaxInactiveInterval(60);
            return JSON.toJSONString(msg);
        } }else {
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
    @PostMapping("/Search") 
    public String Search(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 获取JSON数据
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        System.out.println(responseStrBuilder.toString());
        User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
        List<User> list = loginService.getVagueUserInfo(user);
        Message msg = new Message();

        if (list != null) {
            msg.setCode("success");
            msg.setMsg("成功しました！");
            HttpSession session = req.getSession();
            session.setAttribute("user",list);
            List<User> user1 = (List<User>) session.getAttribute("user");
            msg.setUserList(list);
            session.setMaxInactiveInterval(60);
            return JSON.toJSONString(msg);
        } else {
            msg.setCode("warning");
            msg.setMsg("ユーザーが見つかりませんでした！");
            return JSON.toJSONString(msg);
        }
    }
    @PostMapping("/SearchAll")
    public String SearchAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<User> list = loginService.getAllUserInfo();
        Message msg = new Message();
            msg.setCode("success");
            msg.setMsg("成功しました！");
            HttpSession session = req.getSession();
            session.setAttribute("user",list);
            List<User> user1 = (List<User>) session.getAttribute("user");
            msg.setUserList(list);
            session.setMaxInactiveInterval(60);
            return JSON.toJSONString(msg);
}
    @PostMapping("/SearchDirect")
    public String SearchDirectInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
        User user2 = loginService.getDirectUserInfo(user);
        Message msg = new Message();
        msg.setCode("success");
        msg.setMsg("成功しました！");
        msg.setUser(user2);
        return JSON.toJSONString(msg);
    }
}
