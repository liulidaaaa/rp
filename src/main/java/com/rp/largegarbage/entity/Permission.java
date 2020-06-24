package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/23 17:45
 */
//@Data
@Entity
@Table(name="permission")
public class Permission extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "permission_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer permissionId ;
    //权限名称
    @Column(name = "permission_name",unique = true,nullable = false,columnDefinition = "varchar(255) COMMENT '权限名称'")
    private String permissionName;
    //权限名称
    @Column(name = "permission",unique = true,nullable = false,columnDefinition = "varchar(255) COMMENT '权限描述'")
    private String permission;
    @JsonIgnoreProperties(value = { "permissions" })
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)//配置多表关系
    @JoinTable(name= "role_permission"
            //joinColumns,当前对象在中间表的外键
            ,joinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "permission_id")}
            //inverseJoinColumns，对方对象在中间表的外键
            ,inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    private Set<User> users=new HashSet<User>();

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", permission='" + permission + '\'' +
                ", users=" + users +
                '}';
    }
}
