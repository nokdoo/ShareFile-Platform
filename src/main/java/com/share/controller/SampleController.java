package com.share.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SampleController {

	@GetMapping("/sample1")
	public void sample1(Model model) {
		
		model.addAttribute("greeting","안냐세요");
	}
	
	

	@GetMapping("/kakaoTest")
	public void kakaoTest(Model model) {
		
		System.out.println("!!!!!!!!!!!!!!!!");
		model.addAttribute("greeting","안냐세요");
	}
	
	

	@GetMapping("/kakaoTest2")
	public void kakaoTest2(Model model) {
		

		model.addAttribute("greeting","돌아옴");
	}
	

	@RequestMapping(value="/kakaoLogin", produces = "application/json", method = {RequestMethod.GET,RequestMethod.POST})
	public void kakaoLogin(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse httpservlet)
	{
		
		System.out.println("code : "+code);
	}
	
	
	
	
	
}
