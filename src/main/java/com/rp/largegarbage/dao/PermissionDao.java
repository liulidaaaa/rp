package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionDao extends JpaRepository<Permission,Integer>, JpaSpecificationExecutor<Permission> {
}
