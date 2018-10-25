package com.sharefile.service;

import java.time.LocalDateTime;

import javax.servlet.http.Part;

import com.common.service.Encryptor;
import com.sharefile.domain.FileVO;

public class FileUpload {

	
	public static FileVO storeFile(String storageDirectory, Part part) {
		FileVO file = new FileVO();
		
		String cryptString = Encryptor.GenerateCryptString();
		String fileName = part.getSubmittedFileName();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		
		file.setName(fileName);
		file.setStoredName(cryptString+extension);
		file.setStoredPath(storageDirectory+cryptString+extension);
		file.setAccessdate(LocalDateTime.now());
		file.setRegdate(LocalDateTime.now());
		file.setUploaderId("445566");
		return file;
		
		
	}
	
}
