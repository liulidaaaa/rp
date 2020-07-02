package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.service.RewardPointsService;
import com.rp.largegarbage.service.impl.NoticeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:38
 */
@RestController
@RequestMapping("points")
public class RewardPointsController {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardPointsController.class);

    @Autowired
    private RewardPointsService rewardPointsService;

    /**
     * 我的积分列表
     */
    @PostMapping("initiatorSignIn")
    public ResponseDTO queryMyRewardPoints(Integer userId) {
        return ResponseDTO.buildSuccess(rewardPointsService.queryMyRewardPoints(userId));
    }

    /**
     * 积分规则
     */
    @GetMapping("initiatorSignIn")
    public ResponseDTO RewardRule() {
        return ResponseDTO.buildSuccess(rewardPointsService.RewardRule());
    }
}
