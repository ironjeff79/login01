package com.cls.sitenavi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cls.common.util.Jwt;
import com.alibaba.fastjson.JSON;
import com.cls.sitenavi.entity.Message;
import com.cls.sitenavi.entity.User;
import com.cls.sitenavi.service.ILoginService;

import jakarta.servlet.http.HttpServletRequest;
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
	
	public String login(@RequestBody User user,HttpServletRequest req) {
		
		User list = loginService.confirmUserInfo(user);
		Message msg = new Message();
		if (list != null) {
			if (list.getState().equals("0")) {
				msg.setCode("warning");
				msg.setMsg("ユーザーが無効化されました！");
				return JSON.toJSONString(msg);
			} else {
				msg.setCode("success");
				String token = Jwt.sign(user.getUserId(), user.getPassword());

//				HttpSession session = req.getSession();
//				System.out.println("拦截器中的session的id是====" + session.getId());
//				String sessionId =  session.getId();
//				session.setAttribute("user", list);
//				User user1 = (User) session.getAttribute("user");
				System.out.println("token-----------");
				System.out.println(token);
				msg.setUser(list);
				msg.setToken(token);
//				session.setMaxInactiveInterval(1);
				return JSON.toJSONString(msg);
			}
		} else {
			msg.setCode("warning");
			msg.setMsg("正しいIDとパスワードを入力してください");
			return JSON.toJSONString(msg);
		}
	}

	@PostMapping("/logOut")
	public String logOut(HttpServletRequest req) {
		HttpSession session =req.getSession();
        System.out.println("拦截器中的session的id是====" + session.getId());
//        session.invalidate();
//        String sessionId =  session.getId();
//        System.out.println("登出后中的session的id是====" + session.getId());
        Object obj = session.getAttribute("user");
		User user1 = (User) session.getAttribute("user");
		System.out.println(user1);
		session.setAttribute("user", "");
		String str = session.getAttribute("user").toString();
		System.out.println(str);
		return "ログアウトしました。";
	}

	
	@PostMapping("/portal")
	public String portal(@RequestBody User user) {
		// 获取JSON数据
		User list = loginService.getMail(user);
		return JSON.toJSONString(list);
	}

	@PostMapping("/SearchDirect")
	public String SearchDirectInfo(@RequestBody User user) {
		User user2 = loginService.getDirectUserInfo(user);
		Message msg = new Message();
		msg.setCode("success");
		msg.setUser(user2);
		return JSON.toJSONString(msg);
	}

	@PostMapping("/SearchPage")
	public String SearchPage(@RequestBody Map maps) {
		Map<String, Object> a = loginService.searchPage(maps);
		Message msg = new Message();
		msg.setCode("success");
		msg.setMaps(a);
		return JSON.toJSONString(msg);
	}

	//    @PostMapping("/Search") 
	//    public String Search(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	//        // 获取JSON数据
	//        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
	//        StringBuilder responseStrBuilder = new StringBuilder();
	//        String inputStr;
	//        while ((inputStr = streamReader.readLine()) != null) {
	//            responseStrBuilder.append(inputStr);
	//        }
	//        System.out.println(responseStrBuilder.toString());
	//        User user = JSON.parseObject(responseStrBuilder.toString(), User.class);
	//        List<User> list = loginService.getVagueUserInfo(user);
	//        Message msg = new Message();
	//
	//        if (list != null) {
	//            msg.setCode("success");
	//            msg.setMsg("成功しました！");
	//            HttpSession session = req.getSession();
	//            session.setAttribute("user",list);
	//            List<User> user1 = (List<User>) session.getAttribute("user");
	//            msg.setUserList(list);
	//            session.setMaxInactiveInterval(60);
	//            return JSON.toJSONString(msg);
	//        } else {
	//            msg.setCode("warning");
	//            msg.setMsg("ユーザーが見つかりませんでした！");
	//            return JSON.toJSONString(msg);
	//        }
	//    }
	//    @PostMapping("/SearchAll")
	//    public String SearchAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	//        List<User> list = loginService.getAllUserInfo();
	//        Message msg = new Message();
	//            msg.setCode("success");
	//            msg.setMsg("成功しました！");
	//            HttpSession session = req.getSession();
	//            session.setAttribute("user",list);
	//            List<User> user1 = (List<User>) session.getAttribute("user");
	//            msg.setUserList(list);
	//            session.setMaxInactiveInterval(60);
	//            return JSON.toJSONString(msg);
	//}
}
