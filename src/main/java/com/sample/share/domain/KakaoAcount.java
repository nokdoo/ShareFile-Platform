package com.sample.share.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@Table(name="kakaoAcount")
public class KakaoAcount {

	
	@Id
	private String uid;
	private String nickname;
	
}
