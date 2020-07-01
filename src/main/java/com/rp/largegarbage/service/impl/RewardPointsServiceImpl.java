package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.RewardPointsDao;
import com.rp.largegarbage.service.RewardPointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 10:36
 */
@Service
public class RewardPointsServiceImpl implements RewardPointsService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardPointsServiceImpl.class);
    @Autowired
    private RewardPointsDao rewardPointsDao;

    @Override
    public Map<String, Object> queryMyRewardPoints(Integer userId) {
        //查询积分明细
        //查询总积分
        return null;
    }

    @Override
    public String RewardRule() {
        return null;
    }
}
