package com.rp.largegarbage;

import com.alibaba.fastjson.JSONObject;
import com.rp.largegarbage.dao.CarDao;
import com.rp.largegarbage.dao.OrderGarDao;
import com.rp.largegarbage.dao.RoleDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.*;
import com.rp.largegarbage.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class LargegarbageApplicationTests {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private OrderGarDao orderDao;

	@Resource
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	CarDao carDao;

	@Transactional
	@Rollback(false)
	@Test
	void testFindByUserId() {
		/*User byUserId = userDao.findByUserId(1);
		System.out.println(byUserId);*/
		List<Role> roles = new ArrayList<>();
		roles.add(new Role(1, "临时申请人"));
		roles.add(new Role(2, "发起人"));
		userDao.save(new User(1, roles));
	}

	@Test
	public void testSave() {
		OrderGar order = new OrderGar();
		/*order.setFileInfoId("1,2");
		order.setLng(2.44);
		order.setLat(7.88);
		order.setArea("bb");
		order.setRewardPoints(4);
		order.setDesc("ggbh");
		order.setOrderStatus(0);*/
		//order.setVisitor(1);
		order.setTaskId(4);
		orderDao.saveAndFlush(order);
	}

	@Test
	void saveCar() {
		Car car = new Car();
		car.setCarCode("京78GUB7");
		car.setLng(7.567856);
		car.setLat(89.451834);
		car.setVeo("24");
		car.setDir("212");
		Car save = carDao.saveAndFlush(car);
	}


}
