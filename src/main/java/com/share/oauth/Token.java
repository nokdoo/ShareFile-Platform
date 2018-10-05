package com.share.oauth;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Token {
	
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private String refresh_token_expires_in;
	
	//일단 key, value = access_token, refresh_token;
	static private ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();
	
	public Token(Map<String, String> code) {
		this.access_token = code.get("access_token");
		this.token_type = code.get("token_type");
		this.refresh_token = code.get("refresh_token");
		this.expires_in = code.get("expires_in");
		this.scope = code.get("scope");
		this.refresh_token_expires_in = code.get("refresh_token_expires_in");
	}
	
	public void addToken() {
		tokenStore.put(this.access_token, this.refresh_token);
	}
	
	public void removeToken() {
		tokenStore.remove(this.access_token);
	}
	
	public Token(HttpServletRequest req) throws Exception {
		Cookie cookie = findTokenCookie(req.getCookies());
		if(cookie == null) throw new Exception();
		String[] tokens = cookie.getValue().split("\\.");
		this.access_token = tokens[0];
		this.refresh_token = tokens[1];
	}
	
	private Cookie findTokenCookie(Cookie[] cookies) {
		Cookie cookie = null;
		if(cookies == null) return null;
		for (Cookie c : cookies) {
			if (c.getName().equals("token")) {
				cookie = c;
				break;
			}
		}
		return cookie;
	}
	
	public boolean isLogined() throws IOException {
		if(tokenStore.contains(this.access_token)) {
			System.out.println("이미 로그인 됨");
			return true;
		}
		if(RESTcaller.checkValidity(this)) {
			System.out.println("유효성 체크");
			RESTcaller.updateToken(this);
			return true;
		}
		return false;
	}

	public String getRefreshToken() {
		return this.refresh_token;
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public void setRefreshToken(String refreshToken) {
		this.refresh_token = refreshToken;
	}

}
