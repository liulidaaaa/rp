package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
@Entity
//列name添加索引
@Table(name="user",indexes = {@Index(columnList = "username")})
public class User extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer userId ;
    //用户名
    //@NotEmpty(message="用户名不能为空")
    @Column(name = "username",unique = true,nullable = false,columnDefinition = "varchar(50) COMMENT '用户昵称'")
    private String username;
    //密码
    //@JsonIgnore
    //@Size(min=1,max=10,message="密码的长度应该在1和10之间")
    @Column(name = "password",nullable = false,columnDefinition = "int(127) COMMENT '密码'")
    private Integer password;
    @JsonIgnoreProperties(value = { "users" })
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name= "user_role"
            //joinColumns,当前对象在中间表的外键
            ,joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "user_id")}
            //inverseJoinColumns，对方对象在中间表的外键
            ,inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    private Set<Role> roles=new HashSet<Role>();

    //真实姓名
    @Column(name = "real_name",nullable = true,columnDefinition = "varchar(50) COMMENT '真实姓名'")
    private String realName;
    //性别
    @Column(name = "gender", columnDefinition = "int(10) COMMENT '性别： 1-成功 0-失败'")
    private Integer ender;
    //联系电话
    @JsonProperty("phoneNo")//account
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "phoneNo", columnDefinition = "int(14) COMMENT '联系电话'")
    private Integer phoneNo;
    //常用地址
    @Column(name = "address", columnDefinition = "varchar(500) COMMENT '常用地址'")
    private String address;
    //积分
    @Column(name = "reward_points", columnDefinition = "int(10) COMMENT '奖励积分'")
    private Integer rewardPoints;
    //身份证号
    //@JsonIgnore
    //@Size(min=12,max=20,message="身份证号的长度应该在12和20之间")
    @Column(name = "idcard", columnDefinition = "int(30) COMMENT '身份证号'")
    private Integer idCard;

    @Override
    public String toString() {
        return "{\"User\":"
                + super.toString()
                + ", \"userId\":\"" + userId + "\""
                + ", \"username\":\"" + username + "\""
                + ", \"password\":\"" + password + "\""
                + ", \"roles\":" + roles
                + ", \"realName\":\"" + realName + "\""
                + ", \"ender\":\"" + ender + "\""
                + ", \"phoneNo\":\"" + phoneNo + "\""
                + ", \"address\":\"" + address + "\""
                + ", \"rewardPoints\":\"" + rewardPoints + "\""
                + ", \"idCard\":\"" + idCard + "\""
                + "}";
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getEnder() {
        return ender;
    }

    public void setEnder(Integer ender) {
        this.ender = ender;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Integer getIdCard() {
        return idCard;
    }

    public void setIdCard(Integer idCard) {
        this.idCard = idCard;
    }

    public User() {
    }
    public User(Integer userId, Set<Role> roles) {
        this.userId = userId;
        this.roles = roles;
    }
}
