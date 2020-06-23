package com.rp.largegarbage;

import com.rp.largegarbage.dao.TokenDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.Token;
import com.rp.largegarbage.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class LargegarbageApplicationTests {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenDao tokenDao;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

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
	public void testSaveToken() throws Exception{
		Token token = new Token();
		token.setToken("3");
		token.setUserId(3);
		token.setExpireTime(new Date());
		tokenDao.save(token);
	}
	/**
	 * 添加一个字符串
	 */
	@Test
	public void testSet(){
		this.redisTemplate.opsForValue().set("key", "bobokaoya...");
	}

	/**
	 * 获取一个字符串
	 */
	@Test
	public void testGet(){
		String value = (String)this.redisTemplate.opsForValue().get("key");
		System.out.println(value);
	}

	/**
	 * 添加Users对象
	 */
	@Test
	public void testSetUesrs(){
		User users = new User();
		users.setUserId(1);
		users.setUsername("1");
		//重新设置序列化器
		this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		this.redisTemplate.opsForValue().set("users", users);
	}

	/**
	 * 取Users对象
	 */
	@Test
	public void testGetUsers(){
		//重新设置序列化器
		this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		User users = (User)this.redisTemplate.opsForValue().get("users");
		System.out.println(users);
	}

	/**
	 * 基于JSON格式存Users对象
	 */
	@Test
	public void testSetUsersUseJSON(){
		User users = new User();
		users.setUserId(1);
		users.setUsername("1");
		this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
		this.redisTemplate.opsForValue().set("users_json", users);
	}

	/**
	 * 基于JSON格式取Users对象
	 */
	@Test
	public void testGetUseJSON(){
		this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
		User users = (User)this.redisTemplate.opsForValue().get("users_json");
		System.out.println(users);
	}

}
