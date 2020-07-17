package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.OrderGarDao;
import com.rp.largegarbage.dao.TaskGarDao;
import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.entity.TaskGar;
import com.rp.largegarbage.service.TaskGarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/13 14:14
 */
@Service
public class TaskGarServiceImpl implements TaskGarService {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskGarServiceImpl.class);

    @Autowired
    private TaskGarDao taskGarDao;

    @Autowired
    private OrderGarDao orderGarDao;

    @Override
    public TaskGar createTask(String title, String orderGars, Integer driver, Integer dispatcher, Date cutoffTime) {
        //新增任务信息
        TaskGar taskGar = new TaskGar();
        taskGar.setTitle(title);
        taskGar.setOrderGars(orderGars);
        taskGar.setDriver(driver);
        taskGar.setCutoffTime(cutoffTime);
        taskGar.setTaskStatus(0);
        TaskGar taskGar1 = taskGarDao.saveAndFlush(taskGar);
        //订单绑定到该任务
        String[] strs=orderGars.split(",");
        List<String> list= Arrays.asList(strs);
        for (String s : list) {
            int i = Integer.parseInt(s);
            OrderGar orderGar = orderGarDao.findById(i).get();
            orderGar.setDispatcher(dispatcher);
            orderGar.setDriver(driver);
            //更新垃圾订单状态为已指派
            orderGar.setOrderStatus(2);
            orderGar.setTaskId(taskGar1.getTaskId());
            orderGarDao.save(orderGar);
        }
        return taskGar1;
    }

    @Override
    public Map<String, List<TaskGar>> taskList(Integer driver) {
        Map<String, List<TaskGar>> taskGars = new HashMap<>();
        List<TaskGar> list0 = taskGarDao.findByDriver(driver, 0);
        List<TaskGar> list1 = taskGarDao.findByDriver(driver, 1);
        for (TaskGar taskGar : list1) {
            String orderGars = taskGar.getOrderGars();
            if (null != orderGars) {
                List<String> ids = java.util.Arrays.asList(orderGars.split(","));
                StringBuilder sb = new StringBuilder();
                for (String id : ids) {
                    OrderGar orderGar = orderGarDao.findById(Integer.parseInt(id)).get();
                    String carCode = orderGar.getCarCode();
                    sb.append(carCode);
                    sb.append(",");
                }
                String carCodes = sb.toString();
                carCodes = carCodes.substring(0, carCodes.length() - 1);
                taskGar.setCarCodes(carCodes);
            }
        }
        List<TaskGar> list2 = taskGarDao.findByDriver(driver, 2);
        for (TaskGar taskGar : list2) {
            String orderGars = taskGar.getOrderGars();
            if (null != orderGars) {
                List<String> ids = java.util.Arrays.asList(orderGars.split(","));
                StringBuilder sb = new StringBuilder();
                for (String id : ids) {
                    OrderGar orderGar = orderGarDao.findById(Integer.parseInt(id)).get();
                    String carCode = orderGar.getCarCode();
                    sb.append(carCode);
                    sb.append(",");
                }
                String carCodes = sb.toString();
                carCodes = carCodes.substring(0, carCodes.length() - 1);
                taskGar.setCarCodes(carCodes);
            }
        }
        taskGars.put("新任务", list0);
        taskGars.put("进行中", list1);
        taskGars.put("已完成", list2);
        return taskGars;
    }
    @Override
    public void acceptTask(Integer taskId) {
        TaskGar taskGar = taskGarDao.findById(taskId).get();
        taskGar.setTakeTaskTime(new Date());
        taskGar.setTaskStatus(1);
        taskGarDao.save(taskGar);
    }
    @Override
    public void completeTask(Integer taskId) {
        TaskGar taskGar = taskGarDao.findById(taskId).get();
        taskGar.setTaskStatus(2);
        taskGar.setFinishTaskTime(new Date());
        taskGarDao.save(taskGar);
    }

    @Override
    public void refuseTask(Integer taskId) {
        TaskGar taskGar = taskGarDao.findById(taskId).get();
        taskGar.setTaskStatus(3);
        taskGarDao.save(taskGar);
    }

    @Override
    public void cancelTask(Integer taskId) {
        TaskGar taskGar = taskGarDao.findById(taskId).get();
        taskGar.setTaskStatus(4);
        taskGarDao.save(taskGar);
    }
}
