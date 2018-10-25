package com.sharefile.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sharefile.domain.FileVO;
import com.sharefile.persistence.FileRepository;
import com.sharefile.service.FileUpload;

@Controller
public class ShareFileController {


	@Autowired 
	private FileRepository fileRepo; //DAO
	
	
	@Value("${storageDirectory}")
	String storageDirectory;
	
	
	
	@RequestMapping("/sample/uploadPage")
	public void uploadPage(Model model) {
		//445566 = 현재 로그인된 아이디
		Iterable<FileVO> iterable = fileRepo.findAllByUploaderId("445566"); 
		List<String> imgList = new ArrayList<>();
		List<FileVO> fileList = StreamSupport.stream(iterable.spliterator(), false)
						.peek(f->{
								if(f.getContentType().matches("image/.*")) {
									try(
										FileInputStream fis = new FileInputStream(new File(f.getStoredPath()));
										ByteArrayOutputStream baos = new ByteArrayOutputStream();
									){
										int len = 0;
										byte[] buf = new byte[1024];
								        while ((len = fis.read(buf)) != -1) {
								        	baos.write(buf, 0, len);
								        }
								        byte[] fileArray = baos.toByteArray();
								        String base64 = new String(Base64.encodeBase64(fileArray));
								        imgList.add("data:"+f.getContentType()+";base64,"+base64);
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}else {
									imgList.add("/img/extension/"+f.getExtension()+".png");
								}
							})
						.collect(Collectors.toList());
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("imgList", imgList);
	}
	
	@PostMapping("upload")
	@ResponseStatus(value = HttpStatus.OK)
	public void upload(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		Part part = req.getPart("file");
		
		FileVO file = FileUpload.storeFile(storageDirectory, part);
		fileRepo.save(file);
		part.write(file.getStoredPath());
		System.out.println(file.getStoredPath());
		
		//이미지면 다시 읽어옴
		//리사이징 필요
		if(part.getContentType().matches("image/.*")) {
			BufferedImage image = ImageIO.read(new File(file.getStoredPath()));
			try(
				ServletOutputStream servletOutStream = res.getOutputStream();
			){
				String sMimeType = "image/*";
		        res.setContentType(sMimeType);
		        ImageIO.write(image, file.getExtension(), servletOutStream);
			}
		}
	}
	
	private BufferedImage resizeImage(String path) throws IOException {
		Image originImage = ImageIO.read(new File(path));
		Image resizedImage = originImage.getScaledInstance(180, 80, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(180, 80, BufferedImage.TYPE_INT_RGB);
		Graphics g = newImage.getGraphics();
        g.drawImage(resizedImage, 0, 0, null);
        g.dispose();
        return newImage;
	}
	
	@PostMapping("download")
	@ResponseStatus(value = HttpStatus.OK)
	public void download(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String fileName = req.getHeader("filename");
		System.out.println("fileName : "+fileName);
		FileVO fileVO = fileRepo.findByUploaderIdAndName("445566", fileName);
		String crpytedName = fileVO.getStoredName();
		System.out.println("crpytedName : "+crpytedName);
		byte b[] = new byte[4096];
		try(
			FileInputStream fileInputStream = new FileInputStream(storageDirectory+crpytedName);
			ServletOutputStream servletOutStream = res.getOutputStream();
		) {

	        String sMimeType = "application/octet-stream";
	        
	        res.setContentType(sMimeType);
	        
	        //한글업로드
	        String sEncoding = new String(fileName.getBytes("euc-kr"),"8859_1");
	        res.setHeader("Content-Disposition", "attachment; filename= \"" + sEncoding + "\"");
	        
	        int numRead;
	        while((numRead = fileInputStream.read(b,0,b.length))!= -1){
	            servletOutStream.write(b,0,numRead);            
	        }
		}
	}
}
