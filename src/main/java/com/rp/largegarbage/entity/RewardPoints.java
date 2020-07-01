package com.rp.largegarbage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 10:04
 */
@Data
@Entity
@Table(name="reward_points")
public class RewardPoints extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reward_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer rewardId ;
    //垃圾订单编号
    @Column(name = "order_id",columnDefinition = "int(11) COMMENT '垃圾订单编号'")
    private Integer orderId;
    //用戶编号
    @Column(name = "user_id",columnDefinition = "int(11) COMMENT '用户编号'")
    private Integer userId;
    //商品订单编号
    @Column(name = "order_pro_id",columnDefinition = "int(11) COMMENT '商品订单编号'")
    private Integer orderProId;
    //积分
    @Column(name = "points",columnDefinition = "int(11) COMMENT '积分'")
    private Integer points;
    //正负号
    @Column(name = "plus_or_minus",columnDefinition = "tinyint(1) COMMENT '正负号'")
    private Integer plusOrMinus;

}
