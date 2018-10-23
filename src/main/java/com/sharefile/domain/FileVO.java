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


}
