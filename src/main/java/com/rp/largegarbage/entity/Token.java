package com.rp.largegarbage.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
//列name添加索引
@Table(name="token")
public class Token extends BaseEntity{
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer userId ;
    //
    @Column(name = "token",unique = true,nullable = false,columnDefinition = "varchar(255) COMMENT '用户唯一标识'")
    private String token;

    //过期时间
    @Column(name = "expireTime",nullable = false,columnDefinition = "datetime COMMENT '过期时间'")
    private Date expireTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
