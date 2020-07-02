package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.CarDao;
import com.rp.largegarbage.entity.Car;
import com.rp.largegarbage.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/1 15:06
 */
@Service
public class CarServiceImpl implements CarService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);
    @Autowired
    private CarDao carDao;

    @Override
    public Page<Car> carList() {

        Page<Car> cars = carDao.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "createTime")));
        return cars;
    }
}
