package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.OrderGar;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 11:14
 */
public interface OrderGarService {

    /**
     * 发起人创建订单
     */
    void initiatorCreateOrder(MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer initiator)throws Exception;
    /**
     * 移动端临时申请人创建订单
     */
    void visitCreateOrder(MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer visitor)throws Exception;
    /**
     * 后台发起人审核确认订单
     */
    void confirmOrder(Integer orderId,Integer initiator);
    /**
     * 后台调度人员指派订单
     */
    void distributeOrder(Integer orderId,Integer taskId, Integer dispatcher, Integer driver);
    /**
     * 查看任务所含订单
     */
    List<OrderGar> orderList(Integer taskId);
    /**
     * 移动端司机接单
     */
    void takingOrder(Integer orderId,String carCode);
    /**
     * 移动端司机消单
     */
    void completeOrder(Integer orderId,MultipartFile[] files, double lng, double lat,String desc,Integer driver) throws Exception;
    /**
     * 临时申请人/后台发起人取消订单
     */
    void cancelOrder(Integer orderId);
    /**
     * PC后台订单查询
     */
    Page<OrderGar> queryOrderGarList();


}
