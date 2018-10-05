package com.share.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.share.oauth.RESTcaller;
import com.share.oauth.Token;

@Controller
public class SampleController {

	@GetMapping("/sample1")
	public void sample1(Model model, HttpServletRequest req) {

		model.addAttribute("greeting", "안냐세요");
	}

	@GetMapping("/kakaoTest")
	public void kakaoTest(Model model) {

		System.out.println("!!!!!!!!!!!!!!!!");
		model.addAttribute("greeting", "안냐세요");
	}

	@GetMapping("/kakaoTest2")
	public void kakaoTest2(Model model) {
		model.addAttribute("greeting", "돌아옴");
	}

	/* javascript 로그인 말고 restapi 로그인할 때 필요
	 * @RequestMapping("/oauth")
	public String Ouath(Model model, HttpServletRequest req) {
		model.addAttribute("greeting", "돌아옴");
		System.out.println("YOUN");
		return "sample1";
	}*/

	@RequestMapping("mustLogin")
	public void mustLogin(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		model.addAttribute("greeting", "돌아옴");
		Token token = new Token(req);
		if(!token.isLogined()) throw new Exception("로그인 하자"); // 로그인 필요
		Cookie token_cookie = new Cookie("token", token.getAccess_token()+"."+token.getRefreshToken());		
		res.addCookie(token_cookie);
		
		
		
		// 그냥 만든 것, 사용자 카카오톡 아이디만 가져옴
		String user_id = RESTcaller.getUserInfo(token);
		System.out.println(user_id);
	}
	
	@RequestMapping(value = "/kakaoLogin", produces = "application/json; charset=utf-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestBody Map<String, String> code, HttpServletRequest req, HttpServletResponse res)
			throws UnsupportedEncodingException {
		System.out.println(code);
		Token token = new Token(code);
		token.addToken();
		Cookie token_Cookie = new Cookie("token", token.getAccess_token()+"."+token.getRefreshToken());
		res.addCookie(token_Cookie);
		return "mustLogin";
	}

}
