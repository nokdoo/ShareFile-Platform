package com.sharefile.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.common.service.UserInfo;
import com.google.gson.JsonObject;
import com.sharefile.domain.FileVO;
import com.sharefile.persistence.FileRepository;
import com.sharefile.service.FileService;

@Controller
public class ShareFileController {


	@Autowired 
	private FileRepository fileRepo; //DAO
	
	@Autowired
	private FileService fileService; 
	
	@Autowired UserInfo userInfo;
	
	
	@Value("${storageDirectory}")
	String storageDirectory;
	
	@RequestMapping("/share/uploadPage")
	public void uploadPage(Model model) {
		List<JsonObject> fileList = fileService.getFileList();
		model.addAttribute("fileList", fileList);
	}
	
	@PostMapping("upload")
	@ResponseStatus(value = HttpStatus.OK)
	public void upload(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		Part part = req.getPart("file");
		FileVO file = fileService.storeFile(storageDirectory, part);
		fileRepo.save(file);
		part.write(file.getStoredPath());
	}
	
	@GetMapping("/download/{encoded_filename}")
	@ResponseStatus(value = HttpStatus.OK)
	public void download(@PathVariable("encoded_filename") String encoded_filename, HttpServletResponse res) throws IOException {
		String fileName = encoded_filename;
		String kakaoId = userInfo.getUserId();
		FileVO fileVO = fileRepo.findByNameAndUploaderId(fileName, kakaoId);
		try(
			FileInputStream fileInputStream = new FileInputStream(storageDirectory+fileVO.getStoredName());
			ServletOutputStream servletOutStream = res.getOutputStream();
		) {
	        res.setContentType("application/octet-stream");
	        res.setHeader("Content-Length", String.valueOf(fileVO.getFileSize()));
	        res.setHeader("Content-Disposition", "attachment; filename="
	                + new String(fileName.getBytes("utf-8"), "8859_1") + ";");
	        
	        int numRead;
	        byte b[] = new byte[4096];
	        while((numRead = fileInputStream.read(b,0,b.length))!= -1){
	            servletOutStream.write(b,0,numRead);            
	        }
		}
	}
}
