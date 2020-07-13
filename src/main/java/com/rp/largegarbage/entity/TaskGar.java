package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/13 11:18
 */
@Data
@Entity
@Table(name="task_gar")
public class TaskGar extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "task_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer taskId;
    //任务名称
    @Column(name = "title",nullable = false,columnDefinition = "varchar(127) COMMENT '任务名称'")
    private String title;
    //司机id
    @Column(name = "driver", columnDefinition = "int(11) COMMENT '司机id'")
    private Integer driver;
    //任务完成截止时间
    //@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
    @Column(name = "cutoff_time", columnDefinition = "datetime  COMMENT '截止时间'")
    private Date cutoffTime;
    //任务状态：  0-新任务 1-进行中 2-已完成 3-申请取消 4-已取消
    @Column(name = "task_status", columnDefinition = "int(10) COMMENT '任务状态： 0-新任务 1-进行中 2-已完成 3-申请取消 4-已取消'")
    private Integer taskStatus;

    //任务内容 --订单编号（外键）
    //@OneToMany(mappedBy = "taskGar",cascade=CascadeType.ALL,fetch=FetchType.LAZY)

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskGar")
    //private Set<OrderGar> orderGars;
    /*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="task_id",referencedColumnName = "task_id")
    private Set<OrderGar> orderGars;*/
}
