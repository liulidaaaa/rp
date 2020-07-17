package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.OrderGar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    /*@Transactional
    @Modifying
    @Query(value = "update order_gar o set o.status = 1 where o.order_id = :id", nativeQuery = true)
    void deleteById(@Param(value = "id") Integer id);*/
    @Transactional
    @Modifying
    @Query(value = "update order_gar o set o.status = 1 where o.order_id in :ids", nativeQuery = true)
    void deleteByIds(@Param(value = "ids") List<Integer> ids);
    @Query(value = "select * from order_gar u where u.task_id=?1", nativeQuery = true)
    List<OrderGar> findByTaskId(Integer taskId);
    /*order.setFileInfoId("1,2");
            order.setLng(2.44);
            order.setLat(7.88);
            order.setArea("bb");
            order.setRewardPoints(4);
            order.setDesc("ggbh");
            order.setOrderStatus(0);*/
    //order.setVisitor(1);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO order_gar (file_info_id, lng, lat, area, reward_points, desc, order_status, visitor) VALUES (?1, ?2,?3,?4.?5,?6,?7,?8)", nativeQuery = true)
    int saveOrder(String files, double lng, double lat, String area, Integer rewardPoints, String desc, Integer orderStatus, Integer visitor);
}
