package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.OrderGar;
import com.rp.largegarbage.entity.TaskGar;
import com.rp.largegarbage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 11:14
 */
public interface OrderGarDao extends JpaRepository<OrderGar,Integer>, JpaSpecificationExecutor<OrderGar> {
    @Transactional
    @Modifying
    @Query(value = "update order_gar o set o.order_status = ?1 where o.order_id=?2", nativeQuery = true)
    int updateOrderStatusByOrderId(Integer orderStatus, Integer orderId);
    @Transactional
    @Modifying
    @Query(value = "update order_gar o set o.status = 1 where o.order_id = :id", nativeQuery = true)
    void deleteById(@Param(value = "id") Integer id);
    @Transactional
    @Modifying
    @Query(value = "update order_gar o set o.status = 1 where o.order_id in :ids", nativeQuery = true)
    void deleteByIds(@Param(value = "ids") List<Integer> ids);
    @Query(value = "select * from order_gar u where u.task_id=?1", nativeQuery = true)
    List<OrderGar> findByTaskId(Integer taskId);

}
