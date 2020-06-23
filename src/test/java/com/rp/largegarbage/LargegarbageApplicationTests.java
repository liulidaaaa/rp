package com.rp.largegarbage;

import com.rp.largegarbage.dao.TokenDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.Token;
import com.rp.largegarbage.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class LargegarbageApplicationTests {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenDao tokenDao;


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

}
