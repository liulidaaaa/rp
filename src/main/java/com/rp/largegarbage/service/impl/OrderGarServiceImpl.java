package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.OrderGarDao;
import com.rp.largegarbage.dao.RewardPointsDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.dto.ResponseDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 11:15
 */
@Service
public class OrderGarServiceImpl implements OrderGarService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderGarServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderGarDao orderDao;

    @Autowired
    private RewardPointsDao rewardPointsDao;

    @Autowired
    private FileService fileService;

    @Override
    public void initiatorCreateOrder(MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer initiator) throws Exception {
        //上传图片,返回集合ids
        ResponseDTO responseDTO = fileService.batchUpload(files);
        ArrayList<String> fileId = (ArrayList)responseDTO.getData();
        String ids = StringUtils.join(fileId, ",");
        //创建新订单
        OrderGar order = new OrderGar();
        order.setFileInfoId(ids);
        order.setLng(lng);
        order.setLat(lat);
        order.setArea(area);
        order.setRewardPoints(rewardPoints);
        order.setDesc(desc);
        order.setOrderStatus(1);
        order.setVisitor(initiator);
        orderDao.save(order);
    }

    /**
     * 临时申请人创建订单
     */
    @Override
    public void visitCreateOrder(MultipartFile[] files, double lng, double lat, String area, Integer rewardPoints,String desc,Integer visitorId) throws Exception{
        //上传图片,返回集合ids
        ResponseDTO responseDTO = fileService.batchUpload(files);
        ArrayList<String> fileId = (ArrayList)responseDTO.getData();
        String ids = StringUtils.join(fileId, ",");
        //创建新订单
        OrderGar order = new OrderGar();
        order.setFileInfoId(ids);
        order.setLng(lng);
        order.setLat(lat);
        order.setArea(area);
        order.setRewardPoints(rewardPoints);
        order.setDesc(desc);
        order.setOrderStatus(0);
        order.setVisitor(visitorId);
        orderDao.save(order);
    }

    @Override
    public void confirmOrder(Integer orderId,Integer initiator) {
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        order.setInitiator(initiator);
        //更新垃圾订单状态为已确认
        order.setOrderStatus(1);
        orderDao.save(order);
    }

    @Override
    public void distributeOrder(Integer orderId, Integer dispatcher, Integer driver) {
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        order.setDispatcher(dispatcher);
        order.setDriver(driver);
        //更新垃圾订单状态为已指派
        order.setOrderStatus(2);
        orderDao.save(order);
    }

    @Override
    public void takingOrder(Integer orderId,String carCode) {
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        order.setCarCode(carCode);
        order.setTakeTime(new Date());
        //更新垃圾订单状态为已接单
        order.setOrderStatus(3);
        orderDao.save(order);
    }

    @Override
    public void completeOrder(Integer orderId,MultipartFile[] files, double lng, double lat,String desc,Integer driver) throws Exception{
        //查询订单
        Optional<OrderGar> byId = orderDao.findById(orderId);
        OrderGar order = byId.get();
        //上传图片,返回集合ids
        ResponseDTO responseDTO = fileService.batchUpload(files);
        ArrayList<String> fileId = (ArrayList)responseDTO.getData();
        String ids = StringUtils.join(fileId, ",");
        order.setFileInfoIdDri(ids);
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
        byUserId.setRewardPoints(byUserId.getRewardPoints()+save.getRewardPoints());
        userDao.save(byUserId);
    }

    @Override
    public void cancelOrder(Integer orderId) {
        orderDao.deleteById(orderId);
    }

    @Override
    public Page<OrderGar> queryOrderGarList() {
        return orderDao.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "createTime")));
    }


}
