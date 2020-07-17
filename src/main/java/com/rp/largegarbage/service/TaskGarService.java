package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.TaskGar;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 任务
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/13 12:01
 */
public interface TaskGarService {
    /**
     * 调度人员PC后台创建任务
     */
    TaskGar createTask(String title, String orderGars, Integer driver, Integer dispatcher, Date cutoffTime);
    /**
     * 司机查看任务列表
     */
    Map<String, List<TaskGar>> taskList(Integer driver);
    /**
     * 司机接受任务
     */
    void acceptTask(Integer taskId);
    /**
     * 司机完成任务
     */
    void completeTask (Integer taskId);
    /**
     * 司机申请取消任务
     */
    void refuseTask(Integer taskId);
    /**
     * 调度同意取消任务
     */
    void cancelTask(Integer taskId);
}
