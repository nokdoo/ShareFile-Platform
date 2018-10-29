package com.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.user.domain.AccountVO;

@Service
public class UserInfo {
	public String getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((AccountVO)authentication.getPrincipal()).getKakaoId();
		return userId;
	}
}
