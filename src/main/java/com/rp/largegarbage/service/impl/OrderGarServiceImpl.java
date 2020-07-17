package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.FileInfoDao;
import com.rp.largegarbage.dao.OrderGarDao;
import com.rp.largegarbage.dao.RewardPointsDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.FileInfo;
import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.entity.RewardPoints;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.service.FileService;
import com.rp.largegarbage.service.OrderGarService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 11:15
 */
@Service
public class OrderGarServiceImpl implements OrderGarService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderGarServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderGarDao orderDao;

    @Autowired
    private RewardPointsDao rewardPointsDao;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileInfoDao fileInfoDao;

    /**
     * 临时申请人创建订单
     */
    @Override
    public OrderGar initOrder(Integer type, Date appointmentTime, MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer userId) throws Exception {
        if (null == type) {
            return null;
        }
        if (0 == type) {//临时申请人
            //上传图片,返回集合ids
            ResponseDTO responseDTO = fileService.batchUpload(files);
            String fileIds = (String) responseDTO.getData();
            //创建新订单
            OrderGar order = new OrderGar();
            order.setFileInfoId(fileIds);
            order.setLng(lng);
            order.setLat(lat);
            order.setAppointmentTime(appointmentTime);
            order.setArea(area);
            order.setRewardPoints(rewardPoints);
            order.setDes(desc);
            order.setOrderStatus(1);
            order.setVisitor(userId);
            order.setCreateBy(userId.toString());
            return orderDao.saveAndFlush(order);
        } else if (1 == type) {//发起人
            //上传图片,返回集合ids
            ResponseDTO responseDTO = fileService.batchUpload(files);
            String fileIds = (String) responseDTO.getData();
            //创建新订单
            OrderGar order = new OrderGar();
            order.setFileInfoId(fileIds);
            order.setLng(lng);
            order.setLat(lat);
            order.setAppointmentTime(appointmentTime);
            order.setArea(area);
            order.setRewardPoints(rewardPoints);
            order.setDes(desc);
            order.setOrderStatus(0);
            order.setVisitor(userId);
            order.setCreateBy(userId.toString());
            return orderDao.saveAndFlush(order);
        }
        return null;
    }


    @Override
    public void confirmOrder(Integer orderId, Integer initiator) {
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        order.setInitiator(initiator);
        //更新垃圾订单状态为已确认
        order.setOrderStatus(1);
        orderDao.save(order);
    }

    @Override
    public OrderGar remarkOrder(Integer orderId, String remarks) {
        OrderGar orderGar = orderDao.findById(orderId).get();
        orderGar.setRemarks(remarks);
        OrderGar save = orderDao.save(orderGar);
        return save;
    }

    /*@Override
    public void distributeOrder(Integer orderId, Integer taskId, Integer dispatcher, Integer driver) {
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        order.setDispatcher(dispatcher);
        order.setDriver(driver);
        //order.setTaskId(taskId);
        //更新垃圾订单状态为已指派
        order.setOrderStatus(2);
        orderDao.save(order);
    }*/

    @Override
    public List<OrderGar> taskOrders(Integer taskId) {
        //根据任务编号查询订单
        return orderDao.findByTaskId(taskId);
    }

    @Override
    public Map<String,Object> orderInfo(Integer orderId) {
        OrderGar orderGar = orderDao.findById(orderId).get();
        String fileInfoId = orderGar.getFileInfoId();
        List<String> ids = java.util.Arrays.asList(fileInfoId.split(","));
        List<String> filePaths = new ArrayList<>();
        //StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            int i = Integer.parseInt(id);
            FileInfo fileInfo = fileInfoDao.findById(i).get();
            //sb.append(fileInfo.getFilePath());
            //sb.append(",");
            filePaths.add(fileInfo.getFilePath());
        }
        /*String s = sb.toString();
        s = s.substring(0, s.length() - 1);
        orderGar.setFileInfoId(s);*/
        Integer dispatcher = orderGar.getDispatcher();
        Long phoneNo = userDao.findById(dispatcher).get().getPhoneNo();
        orderGar.setDispatcherPhoneNo(phoneNo);
        Map<String,Object> map = new HashMap<>();
        map.put("orderGar",orderGar);
        map.put("filePaths",filePaths);
        return map;
    }

    @Override
    public OrderGar takingOrder(Integer orderId, String carCode) {
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        order.setCarCode(carCode);
        order.setTakeTime(new Date());
        //更新垃圾订单状态为已接单
        order.setOrderStatus(3);
        return orderDao.save(order);
    }

    @Override
    public OrderGar completeOrder(Integer orderId, MultipartFile[] files, double lng, double lat, String desc, Integer driver) throws Exception {
        //查询订单
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        //上传图片,返回集合ids
        ResponseDTO responseDTO = fileService.batchUpload(files);
        String fileIds = (String) responseDTO.getData();
        order.setFileInfoIdDri(fileIds);
        //更新订单
        order.setLngDri(lng);
        order.setLatDri(lat);
        order.setDescDri(desc);
        order.setCompleteTime(new Date());
        //垃圾订单状态为已消单
        order.setOrderStatus(4);
        OrderGar save = orderDao.save(order);
        //发放积分 积分表新增记录
        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setOrderId(save.getOrderId());
        rewardPoints.setPoints(save.getRewardPoints());
        rewardPoints.setPlusOrMinus(0);
        rewardPoints.setUserId(driver);
        rewardPointsDao.save(rewardPoints);
        //更新用户总积分
        User byUserId = userDao.findByUserId(driver);
        byUserId.setRewardPoints(byUserId.getRewardPoints() + save.getRewardPoints());
        userDao.save(byUserId);
        return save;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        orderDao.updateOrderStatusByOrderId(6, orderId);
    }

    @Override
    public void excuseCancelOrder(Integer orderId) {
        orderDao.updateOrderStatusByOrderId(5, orderId);
    }

    @Override
    public Page<OrderGar> queryOrderGarList() {
        return orderDao.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "createTime")));
    }


}
