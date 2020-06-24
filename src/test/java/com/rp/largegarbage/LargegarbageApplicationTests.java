package com.rp.largegarbage;

import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@SpringBootTest
class LargegarbageApplicationTests {

	@Autowired
	private UserDao userDao;


	@Resource
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@Transactional
	@Test
	void testFindByUserId() {
		User byUserId = userDao.findByUserId(1);
		System.out.println(byUserId);
		/*Set<Role>roles = new HashSet<Role>();
		roles.add(new Role(1, "USER"));
		roles.add(new Role(2, "ADMIN"));
		userDao.save(new User("张三", roles));*/
	}

	@Test
	public void testRedis() {
		redisUtil.set("1","1");
		System.out.println(redisUtil.get("1"));
	}

	@Test
	public void testFilepload() {

	}

}
