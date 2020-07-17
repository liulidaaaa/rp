package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/23 17:45
 */
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    //状态 0有效 1无效
    @Column(name = "status", columnDefinition = "int(10) COMMENT '状态： 0-有效 1-无效'")
    private Integer status;
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    @Column(name = "create_time", columnDefinition = "datetime  COMMENT '创建时间'")
    private Date createTime;
    //创建人
    @Column(name = "create_by",nullable = true,columnDefinition = "varchar(50) COMMENT '创建人'")
    private String createBy;
    //修改世间
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
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
        this.status = 0;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
