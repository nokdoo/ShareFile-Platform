package com.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

	
	@GetMapping("/login")
    public String login() {
        return "authentication/login";
    }
	
	
	/*
	@RequestMapping(value="/authenticate", method = {RequestMethod.POST})
	public String authenticate(@RequestParam("accessToken")String accessToken) {
		
		System.out.println("accessToken : " +accessToken);
		System.out.println("인증 시작");
		
		
		
		
        return "authentication/authenticate";
    }*/
	
	
	
	
}
