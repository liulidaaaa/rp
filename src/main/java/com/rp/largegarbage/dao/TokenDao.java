package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TokenDao extends JpaRepository<Token,Integer>, JpaSpecificationExecutor<Token> {
    @Query(value = "select * from token t where t.user_id=?1", nativeQuery = true)
    Token findByUserId(Integer userId);

    @Query(value = "select * from token t where t.token=?1", nativeQuery = true)
    Token findByToken(String token);

}
