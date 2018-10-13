package com.sharefile.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sharefile.domain.FileVO;
import com.sharefile.persistence.FileRepository;

@Controller
public class ShareFileController {


	@Autowired 
	private FileRepository fileRepo; //DAO
	
	
	@Value("${storageDirectory}")
	String storageDirectory;
	
	
	
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
	public void upload(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		Collection<Part> parts = req.getParts();
		
		/*
		List<FileVO> fileList = parts.stream()
									.map(p -> { 
										FileVO fileVO = new FileVO(p); 
										return fileVO;
									}).collect(Collectors.toList());
		*/
		
		for(Part part : parts) {
			FileVO file = new FileVO(storageDirectory, part);
			fileRepo.save(file);
			part.write(file.getStoredPath());
			System.out.println(file.getStoredPath());
		}
	}
	
	@GetMapping("download")
	@ResponseStatus(value = HttpStatus.OK)
	public void download(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String fileName = req.getParameter("filename");
		System.out.println(fileName);
		byte b[] = new byte[4096];
		try(
			FileInputStream fileInputStream = new FileInputStream(storageDirectory+fileName);
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
