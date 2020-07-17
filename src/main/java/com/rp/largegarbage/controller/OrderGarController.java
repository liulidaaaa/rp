package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.service.OrderGarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

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
     * 移动端 临时申请人/发起人创建订单
     * type: 0-临时申请人,1-发起人
     */
    @PostMapping("initOrder")
    public ResponseDTO initOrder(Integer type, String appointmentTime, MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer userId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date parse = sdf.parse(appointmentTime);
        OrderGar orderGar = orderGarService.initOrder(type,parse, files, lng, lat, area, rewardPoints, desc, userId);
        if (null != orderGar) {
            return ResponseDTO.buildSuccess(orderGar, "新增成功");
        }else {
            return ResponseDTO.buildSuccess(orderGar, "新增失败");
        }
    }

    /**
     * 后台发起人审核临时申请人创建的订单
     */
    @PostMapping("confirmOrder")
    public ResponseDTO confirmOrder(Integer orderId, Integer initiator) {
        orderGarService.confirmOrder(orderId, initiator);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 后台调度人员指派订单(无用 见任务创建)
     */
    /*@PostMapping("distributeOrder")
    public ResponseDTO distributeOrder(Integer orderId, Integer taskId, Integer dispatcher, Integer driver) {
        orderGarService.distributeOrder(orderId, taskId, dispatcher, driver);
        return ResponseDTO.buildSuccess("success");
    }*/

    /**
     * 后台调度人员修改订单信息(给订单添加备注)
     */
    @PostMapping("remarkOrder")
    public ResponseDTO remarkOrder(Integer orderId, String remarks) {
        orderGarService.remarkOrder(orderId, remarks);
        return ResponseDTO.buildSuccess("success");
    }



    /**
     * 司机查看任务所含订单
     */
    @GetMapping("taskOrders")
    public ResponseDTO taskOrders(Integer taskId) {
        return ResponseDTO.buildSuccess(orderGarService.taskOrders(taskId));
    }
    /**
     * 司机查看订单详情
     */
    @GetMapping("orderInfo")
    public ResponseDTO orderInfo(Integer orderId) {
        return ResponseDTO.buildSuccess(orderGarService.orderInfo(orderId));
    }

    /**
     * 移动端司机接单
     */
    @PostMapping("takingOrder")
    public ResponseDTO takingOrder(Integer orderId, String carCode) {
        return ResponseDTO.buildSuccess(orderGarService.takingOrder(orderId, carCode),"录入成功");
    }

    /**
     * 移动端司机消单
     */
    @PostMapping("completeOrder")
    public ResponseDTO completeOrder(Integer orderId, MultipartFile[] files, double lng, double lat, String desc, Integer driver) throws Exception {
        return ResponseDTO.buildSuccess(orderGarService.completeOrder(orderId, files, lng, lat, desc, driver),"消单成功");
    }

    /**
     * 临时申请人/后台发起人取消订单/PC管理员批准取消订单
     */
    @PostMapping("cancelOrder")
    public ResponseDTO cancelOrder(Integer orderId) {
        orderGarService.cancelOrder(orderId);
        return ResponseDTO.buildSuccess("success");
    }
    /**
     * 司机申请取消订单
     */
    @PostMapping("excuseCancelOrder")
    public ResponseDTO excuseCancelOrder(Integer orderId) {
        orderGarService.excuseCancelOrder(orderId);
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
