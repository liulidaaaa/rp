package com.rp.largegarbage.service;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 11:14
 */
public interface OrderService {
    /**
     * 移动端临时申请人创建订单
     */
    public void visitCreateOrder();
    /**
     * 后台发起人审核确认订单
     */
    public void confirmOrder();
    /**
     * 后台调度人员指派订单
     */
    public void distributeOrder();
    /**
     * 移动端司机接单
     */
    public void takingOrder();
    /**
     * 移动端司机消单
     */
    public void completeOrder();
    /**
     * 临时申请人/后台发起人取消订单
     */
    public void cancelOrder();
    /**
     * 给临时申请人/司机发放积分
     */
    public void issuePoints();


}
