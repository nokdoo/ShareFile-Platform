package com.user.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="account")
	public class AccountVO{
		
		@Id
		@GeneratedValue
		private Long id;
		
		@Column(nullable = false, unique = true, length=50)
		private String kakaoId;
		
		private String nickname;
		
		@GeneratedValue(strategy=GenerationType.AUTO)
		private long tailCode;
		
		@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
		@JoinColumn(name="kakaoId")
		private List<AuthorityVO> roles;
	}


	
	