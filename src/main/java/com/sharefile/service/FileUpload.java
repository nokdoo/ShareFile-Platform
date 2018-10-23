package com.sharefile.service;

import java.time.LocalDateTime;

import javax.servlet.http.Part;

import org.springframework.security.crypto.encrypt.Encryptors;

import com.common.service.Encryptor;
import com.sharefile.domain.FileVO;

public class FileUpload {

	
	public static FileVO storeFile(String storageDirectory, Part part) {
		FileVO file = new FileVO();
		String cryptString = Encryptor.GenerateCryptString();
		
		file.setName(part.getSubmittedFileName());
		file.setStoredName(part.getSubmittedFileName());
		file.setStoredPath(storageDirectory+cryptString);
		file.setAccessdate(LocalDateTime.now());
		file.setRegdate(LocalDateTime.now());
		
		return file;
		
		
	}
	
}
