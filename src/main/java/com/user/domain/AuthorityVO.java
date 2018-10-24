package com.user.domain;

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
@Table(name="authority")
public class AuthorityVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	private String authorityName;
	private String groupName;
	
}
