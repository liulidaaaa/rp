package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.Car;
import org.springframework.data.domain.Page;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/1 15:05
 */
public interface CarService {
    Page<Car> carList();
}
