package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/1 13:27
 */
public interface UserRoleDao  extends JpaRepository<UserRole,Integer>, JpaSpecificationExecutor<UserRole> {

}
