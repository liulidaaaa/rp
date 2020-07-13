package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.entity.TaskGar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/13 11:58
 */
public interface TaskGarDao  extends JpaRepository<TaskGar,Integer>, JpaSpecificationExecutor<TaskGar> {
    @Query(value = "select * from task_gar u where u.driver=?1 and task_status = ?2", nativeQuery = true)
    List<TaskGar> findByDriver(Integer driver, Integer taskStatus);
}
