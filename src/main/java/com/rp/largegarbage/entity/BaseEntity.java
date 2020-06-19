package com.rp.largegarbage.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id ;
    //状态 0有效 1无效
    //private Integer state;
    @Column(name = "status", columnDefinition = "int(10) COMMENT '状态： 0-有效 1-无效'")
    private Integer status;
    //创建时间
    @Column(name = "create_time", columnDefinition = "datetime  COMMENT '创建时间'")
    private Date createTime;
    //创建人
    @Column(name = "create_by",nullable = true,columnDefinition = "varchar(50) COMMENT '创建人'")
    private String createBy;
    //修改世间
    @Column(name = "update_time", columnDefinition = "datetime  COMMENT '修改时间'")
    private Date updateTime;
    //修改人
    @Column(name = "update_by",nullable = true,columnDefinition = "varchar(50) COMMENT '修改人'")
    private String updateBy;

    /**
     * 数据插入前的操作
     */
    @PrePersist
    public void setInsertBefore() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    /**
     * 数据修改前的操作
     */
    @PreUpdate
    public void setUpdateBefore() {
        this.updateTime = new Date();
    }

}
