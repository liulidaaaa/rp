package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:17
 */
public interface CarDao extends JpaRepository<Car,Integer>, JpaSpecificationExecutor<Car> {
}
