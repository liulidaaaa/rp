package com.rp.largegarbage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/** 车辆信息
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 17:50
 */
@Data
@Entity
@Table(name="car")
public class Car  extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "car_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer carId ;
    //车辆编号
    @Column(name = "car_code",unique = true,nullable = false,columnDefinition = "varchar(20) COMMENT '车辆编号'")
    private String carCode;
    //经度
    @Column(name = "lng",nullable = false,columnDefinition = "double COMMENT '经度'")
    private double lng;
    //纬度
    @Column(name = "lat",nullable = false,columnDefinition = "double COMMENT '纬度'")
    private double lat;
    //速度
    @Column(name = "veo",nullable = false,columnDefinition = "varchar(20) COMMENT '速度'")
    private String veo;
    //方向
    @Column(name = "dir",nullable = false,columnDefinition = "varchar(20) COMMENT '方向'")
    private String dir;
}
