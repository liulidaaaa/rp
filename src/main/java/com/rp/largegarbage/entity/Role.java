package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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
@Table(name="role")
public class Role extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer roleId ;

    //角色名称
    @Column(name = "role_name",unique = true,nullable = false,columnDefinition = "varchar(255) COMMENT '角色名称'")
    private String roleName;

    /**
     *配置多对多
     */
    @JsonIgnoreProperties(value = { "roles" })
    @ManyToMany(targetEntity = User.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)//配置多表关系
    @JoinTable(name= "user_role"
            //joinColumns,当前对象在中间表的外键
            ,joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")}
            //inverseJoinColumns，对方对象在中间表的外键
            ,inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")})
    private Set<User> users=new HashSet<User>();
    @JsonIgnoreProperties(value = { "roles" })
    @ManyToMany(targetEntity = Permission.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name= "role_permission"
            ,joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")}
            ,inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "permission_id")})
    private Set<Permission> permissions = new HashSet<Permission>();

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
