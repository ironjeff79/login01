package com.cls.sitenavi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.IRegisterService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class RegisterController {
    @Autowired
    public IRegisterService registerService;
    @GetMapping("/register")
    public String register() {
        return String.format("111");
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){
        //ユーザー名とメールが重複するかどうかの判断
        List<Map<String, Object>> list = registerService.getUserId(user.getUserId());
        List<Map<String, Object>> list2 = registerService.getMail(user.getMail());
        Message msg = new Message();
        if (list.size() > 0) {
            msg.setCode("warning");
            msg.setMsg("ユーザー名が重複しています");
            return JSON.toJSONString(msg);
        } else if (list2.size() > 0) {
            msg.setCode("warning");
            msg.setMsg("メールアドレスはすでに使用されています");
            return JSON.toJSONString(msg);
        } else {
            registerService.insertUserInfo(user);
            msg.setCode("success");
            msg.setMsg("ユーザーが登録しました。");
            return JSON.toJSONString(msg);
        }
    }
}