package com.security.component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.security.service.CustomUserDetailsService;
import com.user.domain.AccountVO;


@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	
	
    @Autowired
	CustomUserDetailsService customUserDetailsService;
    
    
    
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = authentication.getName();
        //String password = (String) authentication.getCredentials();
        
        AccountVO accountVO = customUserDetailsService.authenticate(accessToken);
        		
        if (accountVO == null)
            throw new BadCredentialsException("Login Error!!");
        
        System.out.println(accountVO.getKakaoId());
        
        //accountVO.setPassword(null);
 
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(accountVO, null, authorities);
        
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	

}
