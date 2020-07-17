package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.Role;
import com.rp.largegarbage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/23 17:45
 */
public interface RoleDao extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role> {
    /*@Query(value = "select * \n" +
            "FROM role r LEFT JOIN user_role ur on r.role_id=ur.role_id\n" +
            "LEFT JOIN user u ON u.user_id = ur.user_id\n" +
            "where r.role_name=?1", nativeQuery = true)*/
    @Query(value = "select * FROM role r where r.role_name=?1", nativeQuery = true)
    Role findByRoleName(String roleName);
    @Query(value = "select * FROM role r where r.role_id=?1", nativeQuery = true)
    Role findByRoleId(Integer roleId);
}
