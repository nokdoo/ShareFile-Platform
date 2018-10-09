package com.common.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.domain.AccountVO;
import com.common.persistence.AccountRepository;
import com.common.service.KakaoLogin;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class CommonController {
	
	@Autowired 
	private AccountRepository accountRepo;
	
	
	@Autowired 
	private ServletContext servletContext;
	
	@Autowired private 
	ResourceLoader resourceLoader;

	
	@GetMapping("/login")
	public String login(Model model) {
	
		//System.out.println(${storageDirectory});
		return "/common/login";
	}
	
	
	
	
	@RequestMapping(value="/kakaoLogin", produces = "application/json;", method = {RequestMethod.GET,RequestMethod.POST})
	public String kakaoLogin(@RequestParam("code")String code, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception
	{
		System.out.println("code : "+code);
		JsonNode token = KakaoLogin.getAccessToken(code);
		JsonNode profile = KakaoLogin.getKakaoUserInfo(token.path("access_token").toString());
		System.out.println(profile);
		AccountVO account = KakaoLogin.changeData(profile);
		account.setNickname("k"+account.getNickname());
		
		System.out.println(session);
		session.setAttribute("login", account);
		System.out.println(account.toString());
		
		return "/sample1";
		
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		
		AccountVO account = new AccountVO();
		account.setKakaoID("KAKAO ID!");
		accountRepo.save(account);
		return "temp/temp";
	}
	
	@RequestMapping(value="/kakaoLoginTemp", produces = "application/json;", method = {RequestMethod.GET,RequestMethod.POST})
	public void kakaoLogin(@RequestBody String code, HttpServletRequest request, HttpServletResponse httpservlet)
	{
		System.out.println("code!!!!!:"+code);
		System.out.println("야!!!!!!!!!!!!");
	}
	
	public class token2{
		int a;
	}
}

