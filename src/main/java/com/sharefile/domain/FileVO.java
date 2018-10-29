package com.sharefile.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String extension;
	private String contentType;
	private long fileSize;
	private LocalDateTime regdate;
	private LocalDateTime accessdate;


}
