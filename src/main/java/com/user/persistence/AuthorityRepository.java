package com.user.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.user.domain.AuthorityVO;

public interface AuthorityRepository extends CrudRepository<AuthorityVO, Long> {

	//public List<AuthorityVO> findAllRoleById(String kakaoId);

}
