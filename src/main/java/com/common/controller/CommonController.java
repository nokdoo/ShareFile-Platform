package com.common.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.domain.AccountVO;
import com.common.persistence.AccountRepository;



@Controller
public class CommonController {
	
	@Autowired 
	private AccountRepository accountRepo;
	
	
	@GetMapping("/test")
	public String test(Model model) {
		
		AccountVO account = new AccountVO();
		account.setKakaoID("KAKAO ID!");
		accountRepo.save(account);
		
		
		return "sample2";
	}
	
	
	
	
}
