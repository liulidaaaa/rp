package com.rp.largegarbage.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/24 17:59
 */
@Data
@Entity
@Table(name="order_gar")
public class OrderGar extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer orderId ;
    //发起人上传垃圾照片ids
    //@NotEmpty(message="图片不能为空")
    @Column(name = "file_info_id",nullable = false,columnDefinition = "varchar(128) COMMENT '发起人上传垃圾照片ids'")
    private String fileInfoId;
    //垃圾经度
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "lng",nullable = false,columnDefinition = "double COMMENT '垃圾经度'")
    private double lng;
    //垃圾纬度
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "lat",nullable = false,columnDefinition = "double COMMENT '垃圾纬度'")
    private double lat;
    //发起人描述估量
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "desc", nullable = false,columnDefinition = "varchar(500) COMMENT '发起人描述估量'")
    private String desc;
    //积分
    @Column(name = "reward_points", columnDefinition = "int(10) COMMENT '奖励积分'")
    private Integer rewardPoints;

    //订单状态： 0-新订单 1-已确认 2-已指派 3-已接单 4-已消单 5-申请取消 6-已取消'
    @Column(name = "order_status", columnDefinition = "int(10) COMMENT '订单状态： 订单状态： 0-新订单 1-已确认 2-已指派 3-已接单 4-已消单 5-申请取消 6-已取消'")
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

    //司机经度
    @Column(name = "lng_dri",nullable = true,columnDefinition = "double COMMENT '司机经度'")
    private double lngDri;
    //司机纬度
    @Column(name = "lat_dri",nullable = true,columnDefinition = "double COMMENT '司机纬度'")
    private double latDri;
    //司机描述估量
    @Column(name = "desc_dri", nullable = true,columnDefinition = "varchar(500) COMMENT '司机描述估量'")
    private String descDri;
    //订单所在区域
    @Column(name = "area", nullable = false,columnDefinition = "varchar(127) COMMENT '订单所在区域'")
    private String area;
    //管理者核实量方
    @Column(name = "amount", nullable = true,columnDefinition = "int(11) COMMENT '管理者核实量方'")
    private Integer amount;
    //车牌号
    @Column(name = "car_code", nullable = true,columnDefinition = "varchar(20) COMMENT '车牌号'")
    private String carCode;
    //司机接单时间
    @Column(name = "take_time", columnDefinition = "datetime  COMMENT '司机接单时间'")
    private Date takeTime;
    //司机消单时间
    @Column(name = "complete_time", columnDefinition = "datetime  COMMENT '司机消单时间'")
    private Date completeTime;
    //司机上传垃圾照片ids
    //@NotEmpty(message="图片不能为空")
    @Column(name = "file_info_id_dri",nullable = true,columnDefinition = "varchar(128) COMMENT '司机上传照片ids'")
    private String fileInfoIdDri;
    //任务编号（外键）
    @Column(name = "task_id", nullable = true, columnDefinition = "int(11) COMMENT '任务编号（外键）'")
    private Integer taskId;
    //可选属性optional=false,表示taskGar不能为空。删除订单，不影响任务

    /*@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=true)
    //设置在order_gar表中的关联字段(外键)
    @JoinColumn(name="task_id")*/
    /*@ManyToOne
    @JoinColumn(name="task_id")*/
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")*/
    //private TaskGar taskGar;

    public Integer getAmount() {
        return amount;
    }

    public String getFileInfoIdDri() {
        return fileInfoIdDri;
    }

    public void setFileInfoIdDri(String fileInfoIdDri) {
        this.fileInfoIdDri = fileInfoIdDri;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

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

    public Integer getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Integer dispatcher) {
        this.dispatcher = dispatcher;
    }

    public double getLngDri() {
        return lngDri;
    }

    public void setLngDri(double lngDri) {
        this.lngDri = lngDri;
    }

    public double getLatDri() {
        return latDri;
    }

    public void setLatDri(double latDri) {
        this.latDri = latDri;
    }

    public String getDescDri() {
        return descDri;
    }

    public void setDescDri(String descDri) {
        this.descDri = descDri;
    }




}
