package com.share.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.share.domain.KakaoAcount;
@Repository
public interface KakaoAccountRepository extends CrudRepository<KakaoAcount, String>{

	
}
