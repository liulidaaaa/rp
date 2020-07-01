package com.rp.largegarbage.service;

import java.util.Map;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 10:29
 */
public interface RewardPointsService {
    /**
     * 我的积分列表
     */
    public Map<String,Object> queryMyRewardPoints(Integer userId);
    /**
     * 积分规则
     */
    public String RewardRule();
}
