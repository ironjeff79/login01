package com.cls.sitenavi.controllers;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.IChangeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class ChangeInfoController {
    @Autowired
    public IChangeService changeService;

    @GetMapping("/changeInfo")
    public String hello1() {
        return String.format("ログインしました");
    }

    @PostMapping("/changeInfo")
    public String changeInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 获取JSON数据
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
        System.out.println("user");
        System.out.println(user);
//        List<Map<String, Object>> list = changeService.changeUserInfo(user.getUserId(), user.getNewUserId(), user.getPassword());
//        List<Map<String, Object>> list2 = changeService.changeUserInfo(user.getUserId(), user.getNewUserId(), user.getPassword());
        Message msg = new Message();

        if (null == user.getNewUserId()) {
            msg.setCode("success");
            msg.setMsg("パスワードが変更しました！");
            System.out.println("1");
            changeService.changePass(user.getUserId(), user.getPassword());
            return JSON.toJSONString(msg);

        } else {
            msg.setCode("success");
            msg.setMsg("ユーザー情報が変更しました");
            changeService.changeUserInfo(user);
            System.out.println("!!");
            return JSON.toJSONString(msg);
        }
    }
}
