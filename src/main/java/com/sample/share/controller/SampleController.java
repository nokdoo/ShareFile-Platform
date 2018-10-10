package com.sample.share.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

import com.sharefile.domain.FileVO;

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
	public void uploadPage(Model model) {
		File folder = new File(storageDirectory);
		File[] listOfFiles = folder.listFiles();
		
		List<String> fileList = Arrays.stream(listOfFiles)
		.map(File::getName).collect(Collectors.toList());
		
		model.addAttribute("fileList", fileList);

	}
	
	@PostMapping("upload")
	@ResponseStatus(value = HttpStatus.OK)
	public void upload(HttpServletRequest req) throws IOException, ServletException{
		Collection<Part> parts = req.getParts();
		List<FileVO> fileList = parts.stream()
									.map(p -> { 
										FileVO fileVO = new FileVO(p); 
										return fileVO;
									}).collect(Collectors.toList());
		
		for(Part part : parts) {
			String filename = part.getSubmittedFileName();
			part.write(storageDirectory+filename);
			System.out.println(storageDirectory+filename);
			FileVO fileVO = new FileVO(part);
		}
	}
	
	@GetMapping("download")
	@ResponseStatus(value = HttpStatus.OK)
	public void download(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String filePath = storageDirectory+"asd1.pdf";
		String fileName = "asd1.pdf";
		byte b[] = new byte[4096];
		try(
			FileInputStream fileInputStream = new FileInputStream(filePath);
			ServletOutputStream servletOutStream = res.getOutputStream();
		) {

	        String sMimeType = "application/octet-stream";
	        
	        res.setContentType(sMimeType);
	        
	        //한글업로드
	        String sEncoding = new String(fileName.getBytes("euc-kr"),"8859_1");
	        res.setHeader("Content-Disposition", "attachment; filename= " + sEncoding);
	        
	        int numRead;
	        while((numRead = fileInputStream.read(b,0,b.length))!= -1){
	            servletOutStream.write(b,0,numRead);            
	        }
		}
	}
}
