package com.cls.sitenavi.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.IChangeService;

//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
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
	public String changePass(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 获取JSON数据
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		User user = JSON.parseObject(responseStrBuilder.toString(), User.class);

		Message msg = new Message();
		msg.setCode("success");
		msg.setMsg("パスワードが変更しました");
		changeService.changePass(user);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/deleteUser")
	public String deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 获取JSON数据
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
		Message msg = new Message();
		msg.setCode("success");
		msg.setMsg("ユーザーアカウントが削除しました。");
		changeService.deleteUser(user);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/changeAdmin")
	public String changeAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 获取JSON数据
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
		Message msg = new Message();
		changeService.changeAdmin(user);
		msg.setCode("success");
		msg.setMsg("ユーザー情報が更新しました。");
		return JSON.toJSONString(msg);

	}

	@PostMapping("/activeState")
	public String activeState(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 获取JSON数据
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		User user = JSON.parseObject(responseStrBuilder.toString(), User.class);

		Message msg = new Message();
		msg.setCode("success");
		msg.setMsg("ユーザーが有効化しました。");
		changeService.changeState(user);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/inactiveState")
	public String inactiveState(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// 获取JSON数据
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			responseStrBuilder.append(inputStr);
		}
		User user = JSON.parseObject(responseStrBuilder.toString(), User.class);

		Message msg = new Message();
		msg.setCode("success");
		msg.setMsg("ユーザーが無効化しました。");
		changeService.changeState(user);
		return JSON.toJSONString(msg);
	}
}
