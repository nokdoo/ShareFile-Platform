package com.common.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.common.service.KakaoLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.user.domain.AccountVO;
import com.user.persistence.AccountRepository;

@Controller
public class CommonController {
	
	@Autowired 
	private AccountRepository accountRepo;
	

	@Value("${storageDirectory}")
	String storageDirectory;
	


	@GetMapping("/login")
	public String login(Model model) {
	
		//System.out.println(${storageDirectory});
		return "/common/login";
	}
	
	

	@GetMapping("/home")
	public String home(Model model) {
	
		//System.out.println(${storageDirectory});
		return "/home";
	}
	
	
	
	
	////////아래로 삭제 예정
	
	
	@GetMapping("/temp1")
	public String temp1(Model model) {
		
		
		
		
		return "/commonLayout";
	}
	
	
	@GetMapping("/temp2")
	public String temp2(Model model) {
		
		
		
		
		return "/createdPage";
	}
	
	
	@GetMapping("/temp3")
	public String temp3(Model model) {
		
		
		
		
		return "/rowPage";
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/kakaoLogin", produces = "application/json;", method = {RequestMethod.GET,RequestMethod.POST})
	public String kakaoLogin(@RequestParam("code")String code, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception
	{
		System.out.println("code : "+code);
		JsonNode token = KakaoLogin.getAccessToken(code);
		System.out.println("token : "+token);
		
		JsonNode kakaoId = KakaoLogin.getId(token.path("access_token").toString());
		System.out.println("kakao Id : "+kakaoId);
		JsonNode profile = KakaoLogin.getKakaoUserInfo(token.path("access_token").toString());
		System.out.println("profile : "+profile);
		AccountVO account = KakaoLogin.changeData(profile);
		System.out.println(account.getTailCode()+"!!");
		
		
		
		//if(){} //DB에 없는 kakaoID일시 계정 DB 등록
		
		
		
		
		
		//account.setNickname("k"+account.getNickname());
		
		System.out.println(session);
		session.setAttribute("login", account);
		System.out.println(account.toString());
		
		return "/sample/sample1";
		
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		
		AccountVO account = new AccountVO();
		account.setKakaoId("KAKAO ID!");
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

