package com.common.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.common.domain.AccountVO;
import com.common.persistence.AccountRepository;
import com.common.service.KakaoLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

import antlr.Token;



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

