package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.TaskGarDao;
import com.rp.largegarbage.entity.TaskGar;
import com.rp.largegarbage.service.TaskGarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

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

    @Override
    public void createTask(String title, Integer driver, Date cutoffTime) {
        TaskGar taskGar = new TaskGar();
        taskGar.setTitle(title);
        taskGar.setDriver(driver);
        taskGar.setCutoffTime(cutoffTime);
        taskGar.setTaskStatus(0);
        taskGarDao.saveAndFlush(taskGar);
    }

    @Override
    public List<TaskGar> taskList(Integer driver, Integer taskStatus) {
        return taskGarDao.findByDriver(driver, taskStatus);
    }
    @Override
    public void acceptTask(Integer taskId) {
        TaskGar taskGar = taskGarDao.findById(taskId).get();
        taskGar.setTaskStatus(1);
        taskGarDao.save(taskGar);
    }
    @Override
    public void completeTask(Integer taskId) {
        TaskGar taskGar = taskGarDao.findById(taskId).get();
        taskGar.setTaskStatus(2);
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
