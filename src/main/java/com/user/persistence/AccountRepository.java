package com.user.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.user.domain.AccountVO;

@Repository
public interface AccountRepository extends CrudRepository<AccountVO, String> {

	public AccountVO findBykakaoId(String kakaoID);
	
}

