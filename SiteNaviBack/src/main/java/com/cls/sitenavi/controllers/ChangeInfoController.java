package com.cls.sitenavi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.IChangeService;
import com.cls.sitenavi.service.ILoginService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class ChangeInfoController {
	@Autowired
	public IChangeService changeService;
	
	@Autowired
	public ILoginService loginService;
	
//	@GetMapping("changeInfo")
//	public String hello1() {
//		return String.format("ログインしました");
//	}

	
	

	@PostMapping("/changeInfo")
	public String changeInfo(@RequestBody User user) {
		System.out.println("進入changeInfo");
		User list = changeService.getInfo(user);
		Message msg = new Message();
		if (list != null) {
			msg.setCode("warning");
			msg.setMsg("メールは重複しています！");
			return JSON.toJSONString(msg);
		} else {
			msg.setCode("success");
			msg.setMsg("ユーザー情報が変更しました");
			changeService.changeInfo(user);
			return JSON.toJSONString(msg);
		}
	}

	@PostMapping("/changePass")
	public String changePass(@RequestBody User user) {
		System.out.println("進入changePass");
		Message msg = new Message();
		msg.setCode("success");
		msg.setMsg("パスワードが変更しました");
		changeService.changePass(user);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/deleteUser")
	public String deleteUser(@RequestBody User user) {
		Message msg = new Message();
		msg.setCode("success");
		msg.setMsg("ユーザーアカウントが削除しました。");
		changeService.deleteUser(user);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/changeAdmin")
	public String changeAdmin(@RequestBody User user) {
		Message msg = new Message();
		changeService.changeAdmin(user);
		msg.setCode("success");
		msg.setMsg("ユーザー情報が更新しました。");
		return JSON.toJSONString(msg);

	}

	@PostMapping("/changeState")
	public String activeState(@RequestBody User user) {
		Message msg = new Message();
		changeService.changeState(user);
		if (user.getState().equals("1")) {
			msg.setCode("success");
			msg.setMsg("ユーザーが有効化しました。");
			return JSON.toJSONString(msg);
		} else if (user.getState().equals("0")) {
			msg.setCode("success");
			msg.setMsg("ユーザーが無効化しました。");
			return JSON.toJSONString(msg);
		}
		return null;
	}
}
