package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/24 17:59
 */
@Data
@Entity
//列name添加索引
@Table(name="order")
public class Order extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer orderId ;
    //垃圾照片id
    //@NotEmpty(message="图片不能为空")
    @Column(name = "file_info_id",unique = true,nullable = false,columnDefinition = "varchar(50) COMMENT '垃圾照片'")
    private String fileInfoId;
    //经度
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "lng",nullable = false,columnDefinition = "double COMMENT '经度'")
    private double lng;
    //纬度
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "lat",nullable = false,columnDefinition = "double COMMENT '纬度'")
    private double lat;
    //描述
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "desc", nullable = false,columnDefinition = "varchar(500) COMMENT '描述'")
    private String desc;
    //积分
    @Column(name = "reward_points", columnDefinition = "int(10) COMMENT '奖励积分'")
    private Integer rewardPoints;

    //订单状态： 0-新订单 1-已确认 2-已指派 3-已接单 4-已消单'
    @Column(name = "order_status", columnDefinition = "int(10) COMMENT '订单状态： 订单状态： 0-新订单 1-已确认 2-已指派 3-已接单 4-已消单'")
    private Integer orderStatus;
    //临时申请人id
    @Column(name = "visitor", columnDefinition = "int(11) COMMENT '临时申请人id'")
    private Integer visitor;
    //发起人id(审核人)
    @Column(name = "initiator", columnDefinition = "int(11) COMMENT '发起人(审核人)id'")
    private Integer initiator;
    //调度人id
    @Column(name = "dispatcher", columnDefinition = "int(11) COMMENT '调度人id'")
    private Integer dispatcher;
    //司机id
    @Column(name = "driver", columnDefinition = "int(11) COMMENT '司机id'")
    private Integer driver;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(String fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getVisitor() {
        return visitor;
    }

    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
    }

    public Integer getInitiator() {
        return initiator;
    }

    public void setInitiator(Integer initiator) {
        this.initiator = initiator;
    }

    public Integer getDriver() {
        return driver;
    }

    public void setDriver(Integer driver) {
        this.driver = driver;
    }
}
