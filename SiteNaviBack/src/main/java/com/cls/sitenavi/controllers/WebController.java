package com.cls.sitenavi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.service.IWebService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class WebController {
	@Autowired
	public IWebService webService;

	@PostMapping("/getWebInfo")

	public String getWebInfo(@RequestBody int a) {
		Map<String,Object> webList = webService.getWebInfo(a);
		Message msg = new Message();
		msg.setMsg("success");
		msg.setMaps(webList);
		return JSON.toJSONString(msg);
	}

	

}
