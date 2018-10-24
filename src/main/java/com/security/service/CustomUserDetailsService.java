package com.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.domain.SecurityMember;
import com.user.persistence.AccountRepository;
import com.user.persistence.AuthorityRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    @Autowired
    AccountRepository accountRepo;
    
    @Autowired
    AuthorityRepository authorityRepo; 
    

    
    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {

    	System.out.println("인증이 일어났다!!!!!!!!!!!!!!!! kakaoId : "+kakaoId);
    	System.out.println("findBykakaoId(kakaoId).toString()"+accountRepo.findBykakaoId(kakaoId).toString());
    	
    	
    	
    	return
    			Optional.ofNullable(accountRepo.findBykakaoId(kakaoId))
    			.filter(m-> m!=null)
    			.map(m->new SecurityMember(m)).get();
    	
    			
						

    }
}

