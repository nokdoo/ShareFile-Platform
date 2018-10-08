package com.common.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.common.domain.AccountVO;

@Repository
public interface AccountRepository extends CrudRepository<AccountVO, String> {

}

