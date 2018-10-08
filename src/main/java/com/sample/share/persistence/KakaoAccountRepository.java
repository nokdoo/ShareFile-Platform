package com.sample.share.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sample.share.domain.KakaoAcount;

@Repository
public interface KakaoAccountRepository extends CrudRepository<KakaoAcount, String>{

	
}
