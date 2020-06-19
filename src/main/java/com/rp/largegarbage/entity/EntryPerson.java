package com.rp.largegarbage.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: largegarbage
 * @description: 发起人实体
 * @author: lld
 * @create: 2020-06-18 18:29
 **/

@Data
@Entity
@Table(name="sys_entry_person")
//列phoneNo添加索引
//@Table(name="EntryPerson",indexes = {@Index(columnList = "phoneNo")})
public class EntryPerson extends BaseEntity implements Serializable {

    //真实姓名
    @Column(name = "name",nullable = true,columnDefinition = "varchar(50) COMMENT '姓名'")
    private String name;
    //性别
    @Column(name = "gender", columnDefinition = "int(10) COMMENT '性别： 1-成功 0-失败'")
    private Integer ender;
    //联系电话
    @Column(name = "phoneNo", columnDefinition = "int(14) COMMENT '联系电话'")
    private Integer phoneNo;
    //常用地址
    @Column(name = "address", columnDefinition = "varchar(500) COMMENT '常用地址'")
    private String address;
    //积分
    @Column(name = "reward_points", columnDefinition = "int(10) COMMENT '奖励积分'")
    private Integer rewardPoints;
    //
}
