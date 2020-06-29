package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 11:14
 */
public interface OrderDao extends JpaRepository<Permission,Integer>, JpaSpecificationExecutor<Permission> {

}
