package com.sample.share.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController {

	@GetMapping("/sample1")
	public void sample1(Model model) {
		
		model.addAttribute("greeting","안냐세요");
	}
	
	
	

	@GetMapping("/sample2")
	public void sample2(Model model) {
		
		model.addAttribute("greeting","안냐세요");
	}
	
	

	@GetMapping("kakaoTest")
	public void kakaoTest(Model model) {
		
		System.out.println("!!!!!!!!!!!!!!!!");
		model.addAttribute("greeting","안냐세요");
	}
	
	
	
	
	

	@GetMapping("kakaoTest2")
	public String kakaoTest2(Model model) {
		model.addAttribute("greeting","돌아옴");
		return "sample1";
	}
	

	@RequestMapping(value="/kakaoLogin", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST})
	public void kakaoLogin(@RequestBody String code, HttpServletRequest request, HttpServletResponse httpservlet)
	{
		try {
			code = URLDecoder.decode(code, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("code : "+code);
	}
	
	
	@RequestMapping(value="/koreanTest", produces = "application/json", method = {RequestMethod.GET,RequestMethod.POST})
	public void koreanTest(@RequestBody String code, HttpServletRequest request, HttpServletResponse httpservlet) throws Exception
	{
		String temp = URLDecoder.decode(code, "UTF-8");
		System.out.println("code : "+code);
		System.out.println("code : "+temp);
	}
	
}
