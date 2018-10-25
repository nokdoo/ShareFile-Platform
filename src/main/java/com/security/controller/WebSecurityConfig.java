package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;

import com.security.service.CustomUserDetailsService;

@EnableWebSecurity /* WebConfig 컴포넌트 등록 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	/*
	@Autowired
	PasswordEncoder passwordEncoder;


	@Bean
	public PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
	}
	*/

	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(customUserDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}



	/* Security 제외 패턴 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests() // 인증 요청 선언??????

				.antMatchers("/access").hasRole("ADMIN") // /access 패턴은 ADMIN 유저만 접근 
				.and()
				.formLogin() // 로그인 폼 나오도록 
				.loginPage("/login") //* 내가 만든 로그인 페이지 
				.usernameParameter("kakaoId") // username 을 대체할 아이디 param default username 
				//.passwordParameter("password")  //password 를 대체할 패스워드 param default password 
				.permitAll()  //모두 오픈 ( 반대는 denyAll() ) 
				.and().logout().permitAll().logoutSuccessUrl("/"); // 로그아웃 성공시 리다이렉트 url 
		
		
		http 	.authorizeRequests() 
		//1 
				.antMatchers("/temp1") 
					.permitAll() 
			.and() 
		// 2 
				.formLogin() 
				.usernameParameter("userId") 
				.passwordParameter("userPassword") 
				.loginProcessingUrl("/login") 
				.defaultSuccessUrl("/home") 
					.permitAll() 
			.and() 
		// 3 
		.logout() 
				.logoutUrl("/customLogout") 
				.logoutSuccessUrl("/") 
				.invalidateHttpSession(true) 
					.permitAll() 
			.and() 
			// 4 
		//.csrf().disable() 
		;

		//1) authorizeRequests 밑으로 antMatchers 들을 통해 url 에 대한 권한을 부여할 수 있습니다.
		//2) formLogin 밑으로로그인에 대한 설정(파라미터, url, 핸들러 등등) 할 수 있습니다.
		//3) logout 밑으로 로그아웃에 대한 설정(url, 세션, 쿠키 등등)을 할 수 있습니다.
		//4) csrf CSRF 공격에 대해 방어 하고 토큰을 보낼지 여부.

		
		http 	.authorizeRequests() 
		
				.antMatchers("/temp2") 
					.denyAll()
			.and() 
		
				.formLogin()
				.usernameParameter("userId") 
				.passwordParameter("userPassword") 
				.defaultSuccessUrl("/home") 
					.permitAll() 
			.and() 
		
		.logout() 
				.logoutUrl("/customLogout") 
				.logoutSuccessUrl("/") 
				.invalidateHttpSession(true) 
					.permitAll() 
			.and()
		//.csrf()
		;
		
	} 
}
