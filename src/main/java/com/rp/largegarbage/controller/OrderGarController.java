package com.rp.largegarbage.controller;

import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.service.OrderGarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:37
 */
@RestController
@RequestMapping("order")
public class OrderGarController {

    @Autowired
    private OrderGarService orderGarService;

    /**
     * 移动端临时申请人创建订单
     */
    @PostMapping("visitCreateOrder")
    public void visitCreateOrder(MultipartFile[] files, double lng, double lat, Integer rewardPoints, String desc, Integer visitor) throws Exception {
        orderGarService.visitCreateOrder(files, lng, lat, rewardPoints, desc, visitor);
    }

    /**
     * 后台发起人审核确认订单
     */
    @PostMapping("confirmOrder")
    public void confirmOrder(Integer orderId, Integer initiator) {
        orderGarService.confirmOrder(orderId, initiator);
    }

    /**
     * 后台调度人员指派订单
     */
    @PostMapping("distributeOrder")
    public void distributeOrder(Integer orderId, Integer dispatcher, Integer driver) {
        orderGarService.distributeOrder(orderId, dispatcher, driver);
    }

    /**
     * 移动端司机接单
     */
    @PostMapping("")
    public void takingOrder(Integer orderId, String carCode) {
        orderGarService.takingOrder(orderId, carCode);
    }

    /**
     * 移动端司机消单
     */
    @PostMapping("takingOrder")
    public void completeOrder(Integer orderId, MultipartFile[] files, double lng, double lat, String desc, Integer driver) throws Exception {
        orderGarService.completeOrder(orderId, files, lng, lat, desc, driver);
    }

    /**
     * 临时申请人/后台发起人取消订单
     */
    @PostMapping("cancelOrder")
    public void cancelOrder(Integer orderId) {
        orderGarService.cancelOrder(orderId);
    }

    /**
     * 订单查询
     */
    @GetMapping("queryOrderGarList")
    public List<OrderGar> queryOrderGarList() {
        return orderGarService.queryOrderGarList();
    }
}
