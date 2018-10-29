package com.sharefile.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.service.Encryptor;
import com.common.service.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sharefile.domain.FileVO;
import com.sharefile.persistence.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepo; // DAO

	@Autowired
	private UserInfo userInfo;

	private static final int IMG_WIDTH = 180;
	private static final int IMG_HEIGHT = 100;

	public FileVO storeFile(String storageDirectory, Part part) {
		FileVO file = new FileVO();

		String kakaoId = userInfo.getUserId();

		String cryptString = Encryptor.GenerateCryptString();
		String fileName = part.getSubmittedFileName();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		file.setName(fileName);
		file.setStoredName(cryptString);
		file.setStoredPath(storageDirectory + cryptString);
		file.setExtension(extension);
		file.setAccessdate(LocalDateTime.now());
		file.setRegdate(LocalDateTime.now());
		file.setContentType(part.getContentType());
		file.setUploaderId(kakaoId);
		file.setFileSize(part.getSize());
		return file;
	}

	public List<JsonObject> getFileList() {
		List<JsonObject> jsonList = new LinkedList<JsonObject>();
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();

		String kakaoId = userInfo.getUserId();
		Iterable<FileVO> iterable = fileRepo.findAllByUploaderId(kakaoId);

		StreamSupport.stream(iterable.spliterator(), false).forEach(f -> {
			String jsonOfFile = gson.toJson(f);
			JsonObject obj = parser.parse(jsonOfFile).getAsJsonObject();
			if (f.getContentType().matches("image/.*")) {
				try {
					BufferedImage originImage = ImageIO.read(new File(f.getStoredPath()));
					BufferedImage resizedImage = resizeImage(originImage);
					String base64 = getBase64FromBufferedImage(resizedImage, f.getExtension());
					obj.addProperty("img_src", "data:" + f.getContentType() + ";base64," + base64);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				obj.addProperty("img_src", "/img/extension/" + f.getExtension() + ".png");
			}
			jsonList.add(obj);
		});
		return jsonList;
	}

	public String getBase64FromBufferedImage(BufferedImage bufferedImage, String extension) throws IOException {
		String base64 = null;
		try (ByteArrayOutputStream os = new ByteArrayOutputStream(); OutputStream b64 = new Base64OutputStream(os);) {
			ImageIO.write(bufferedImage, extension, b64);
			base64 = os.toString("UTF-8");
		} catch (Exception e) {
			System.out.println("error : com.sharefile.service.FileService - getBase64FromBufferedImage");
		}
		return base64;
	}

	public BufferedImage resizeImage(BufferedImage originImage) {
		Image scaledImage = originImage.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
		BufferedImage newImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = newImage.getGraphics();
		g.drawImage(scaledImage, 0, 0, null);
		g.dispose();
		return newImage;
	}

}
