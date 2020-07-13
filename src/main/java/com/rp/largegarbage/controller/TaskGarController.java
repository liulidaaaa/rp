package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.service.TaskGarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/13 16:36
 */
@RestController
@RequestMapping("task")
public class TaskGarController {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskGarController.class);

    @Autowired
    private TaskGarService taskGarService;

    /**
     * 调度人员PC后台创建任务
     */
    @PostMapping("createTask")
    public ResponseDTO createTask(String title, Integer driver, Date cutoffTime) {
        taskGarService.createTask(title, driver, cutoffTime);
        return ResponseDTO.buildSuccess("success");
    }

    /**
     * 司机查看任务列表
     */
    @PostMapping("taskList")
    public ResponseDTO taskList(Integer driver, Integer taskStatus){
        return ResponseDTO.buildSuccess(taskGarService.taskList(driver, taskStatus));
    }
    /**
     * 司机接受任务
     */
    @PostMapping("acceptTask")
    public ResponseDTO acceptTask(Integer taskId){
        taskGarService.acceptTask(taskId);
        return ResponseDTO.buildSuccess("success");
    }
    /**
     * 司机完成任务
     */
    @PostMapping("completeTask")
    public ResponseDTO completeTask (Integer taskId){
        taskGarService.completeTask(taskId);
        return ResponseDTO.buildSuccess("success");
    }
    /**
     * 司机申请取消任务
     */
    @PostMapping("refuseTask")
    public ResponseDTO refuseTask(Integer taskId){
        taskGarService.refuseTask(taskId);
        return ResponseDTO.buildSuccess("success");
    }
    /**
     * 调度同意取消任务
     */
    @PostMapping("cancelTask")
    public ResponseDTO cancelTask(Integer taskId){
        taskGarService.cancelTask(taskId);
        return ResponseDTO.buildSuccess("success");
    }
}
