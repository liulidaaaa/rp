package com.rp.largegarbage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/1 13:19
 */
@Data
@Entity
@Table(name="role_permission")
public class RolePermission extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer id ;
    //
    @Column(name = "role_id",nullable = false,columnDefinition = "int(11) COMMENT '角色外键'")
    private String roleId;
    //
    @Column(name = "permission_id",nullable = false,columnDefinition = "int(11) COMMENT '权限外键'")
    private String permissionId;

}
