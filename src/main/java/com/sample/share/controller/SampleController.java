package com.sample.share.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class SampleController {
	
	@Value("${storageDirectory}")
	String storageDirectory;

	@GetMapping("/sample/sample1")
	public void sample1(Model model) {
		
		model.addAttribute("greeting","안냐세요");
	}
	
	@GetMapping("/sample/sample2")
	public void sample2(Model model) {
		
		model.addAttribute("greeting","안냐세요");
	}

	@GetMapping("/sample/kakaoTest")
	public void kakaoTest(Model model) {
		
		System.out.println("!!!!!!!!!!!!!!!!");
		model.addAttribute("greeting","안냐세요");
	}

	@GetMapping("/sample/kakaoTest2")
	public String kakaoTest2(Model model) {
		model.addAttribute("greeting","돌아옴");
		return "kakaoTest2";
	}
	

	@RequestMapping(value="/kakaoLogin123", produces = "application/json; charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST})
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
	
	@RequestMapping("/sample/uploadPage")
	public void uploadPage() {
	}
	
	@PostMapping("upload")
	@ResponseStatus(value = HttpStatus.OK)
	public void upload(HttpServletRequest req) throws IOException, ServletException{
		System.out.println("testupload");
		int i = 0;
		Collection<Part> parts = req.getParts();
		for(Part part : parts) {
			String filename = "asd"+(++i)+".pdf";
			part.write(storageDirectory+filename);
			//part.write("d:\\"+java.io.File.separator + filename);
		}
	}
}
