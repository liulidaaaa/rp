package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    @Query(value = "select * from user u where u.username=?1", nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "select * from user u where u.user_id=?1", nativeQuery = true)
    User findByUserId(Integer userId);

}
