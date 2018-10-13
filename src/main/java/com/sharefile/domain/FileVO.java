package com.sharefile.domain;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "File")
public class FileVO {

	private static final List<Integer> VALID_PWD_CHARS = new ArrayList<>();

	static {
		IntStream.rangeClosed('0', '9').forEach(VALID_PWD_CHARS::add); // 0-9
		IntStream.rangeClosed('A', 'Z').forEach(VALID_PWD_CHARS::add); // A-Z
		IntStream.rangeClosed('a', 'z').forEach(VALID_PWD_CHARS::add); // a-z
	}

	private static String GenerateCryptString() {
		int passwordLength = 31;
		String a = new SecureRandom().ints(passwordLength, 0, VALID_PWD_CHARS.size()).map(VALID_PWD_CHARS::get)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return a;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long fileId;
	private String name;
	private String tag;
	private String uploaderId;
	private String storedName;
	private String storedPath;
	private LocalDateTime regdate;
	private LocalDateTime accessdate;

	public FileVO(String storageDirectory, Part part) {
		String cryptString = GenerateCryptString();
		this.name = part.getSubmittedFileName();
		this.storedName = cryptString;
		this.storedPath = storageDirectory+cryptString;
		this.accessdate = LocalDateTime.now();
		this.regdate = LocalDateTime.now();
	}

}
