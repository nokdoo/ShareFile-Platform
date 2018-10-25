package com.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.domain.SecurityMember;
import com.user.domain.AccountVO;
import com.user.persistence.AccountRepository;
import com.user.persistence.AuthorityRepository;
import com.user.service.KakaoLogin;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    AccountRepository accountRepo;
    
    @Autowired
    AuthorityRepository authorityRepo; 
    

    
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	System.out.println("findBykakaoId(kakaoId).toString()"+accountRepo.findBykakaoId(username).toString());
    	
    	
    	
    	return
    			Optional.ofNullable(accountRepo.findBykakaoId(username))
    			.filter(m-> m!=null)
    			.map(m->new SecurityMember(m)).get();
    	
    }
    
    public AccountVO authenticate(String accessToken) {
    	
    	//실제로 쓰는 토큰인지 검증을 해야함(실제로 쓰는애면 아래 단계로, 실제로 안쓰는애면 null 리턴)
    	String kakaoId  = KakaoLogin.getKakaoIdByTokenValidation(accessToken);
    	if(kakaoId.equals("null"))
    		return null;
    	
    	
    	//DB에 있는애인지 검사 ㄱ (있으면 그애 return, 없으면 새로 등록하고 return)
    	AccountVO accountVO = accountRepo.findBykakaoId(kakaoId);
    	if(accountVO == null)
    	{
    		AccountVO newAccount = new AccountVO();
    		newAccount.setKakaoId(kakaoId);
    		accountRepo.save(newAccount);
    		
    		return newAccount;
    		
    	}
    	
    	return accountVO;
    }
}

