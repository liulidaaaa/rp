package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.service.OrderGarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderGarController.class);

    @Autowired
    private OrderGarService orderGarService;
    /**
     * 发起人创建订单
     */
    @PostMapping("initiatorCreateOrder")
    public ResponseDTO initiatorCreateOrder(MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer initiator)throws Exception{
        orderGarService.initiatorCreateOrder(files,lng,lat,area,rewardPoints,desc,initiator);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 移动端临时申请人创建订单
     */
    @PostMapping("visitCreateOrder")
    public ResponseDTO visitCreateOrder(MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer visitor) throws Exception {
        orderGarService.visitCreateOrder(files,lng,lat,area,rewardPoints,desc,visitor);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 后台发起人审核确认订单
     */
    @PostMapping("confirmOrder")
    public ResponseDTO confirmOrder(Integer orderId, Integer initiator) {
        orderGarService.confirmOrder(orderId, initiator);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 后台调度人员指派订单
     */
    @PostMapping("distributeOrder")
    public ResponseDTO distributeOrder(Integer orderId, Integer taskId, Integer dispatcher, Integer driver) {
        orderGarService.distributeOrder(orderId, taskId, dispatcher, driver);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 移动端司机接单
     */
    @PostMapping("takingOrder")
    public ResponseDTO takingOrder(Integer orderId, String carCode) {
        orderGarService.takingOrder(orderId, carCode);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 移动端司机消单
     */
    @PostMapping("completeOrder")
    public ResponseDTO completeOrder(Integer orderId, MultipartFile[] files, double lng, double lat, String desc, Integer driver) throws Exception {
        orderGarService.completeOrder(orderId, files, lng, lat, desc, driver);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 临时申请人/后台发起人取消订单
     */
    @PostMapping("cancelOrder")
    public ResponseDTO cancelOrder(Integer orderId) {
        orderGarService.cancelOrder(orderId);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * PC订单查询
     */
    @GetMapping("queryOrderGarList")
    public ResponseDTO queryOrderGarList() {
        return ResponseDTO.buildSuccess(orderGarService.queryOrderGarList());
    }
}
