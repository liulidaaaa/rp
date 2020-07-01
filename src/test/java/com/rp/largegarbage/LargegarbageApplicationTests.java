package com.rp.largegarbage;

import com.rp.largegarbage.dao.OrderGarDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.Role;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.redis.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class LargegarbageApplicationTests {

	@Autowired
	private UserDao userDao;

	@Autowired
	private OrderGarDao orderDao;

	@Autowired
	private MyClientSocket clientSocket;

	@Resource
	private RedisTemplate<Object, Object> redisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@Transactional
	@Test
	void testFindByUserId() {
		/*User byUserId = userDao.findByUserId(1);
		System.out.println(byUserId);*/
		Set<Role> roles = new HashSet<Role>();
		roles.add(new Role(1, "临时申请人"));
		roles.add(new Role(2, "发起人"));
		userDao.save(new User(1, roles));
	}
	@Test
	void testOrderGarDao() {
		//orderDao.updateOrderStatusByOrderId(1,1);
		/*List<Integer> ids = new ArrayList();
		ids.add(1);
		orderDao.deleteByIds(ids);*/
		orderDao.deleteById(1);
	}

	@Test
	public void testString(){
		//集合转字符串
		ArrayList<String> fileId = new ArrayList();
		fileId.add("111");
		fileId.add("222");
		String ids = StringUtils.join(fileId, ",");
		System.out.println(ids+"======");

		//字符串转集合
		String str="篮球,足球,排球";
		String[] strs=str.split(",");
		List list= Arrays.asList(strs);
		System.out.println(list+"======");
	}
	@Test
	public void testRedis() {
		redisUtil.set("1","1");
		System.out.println(redisUtil.get("1"));
	}
	@Test
	public void testUploadFile() {
		//ResponseDTO upload = fileUtil.upload(file);
	}

	@Test
	public void testGPS() {
		String jsonLogin = "{\n" +
				"    \"header\": {\"cmd\": \"1000\"},\n" +
				"    \"body\": {\n" +
				"        \"username\": \"zcxt1\",\n" +
				"        \"password\": \"000000\",\n" +
				"        \"ver\": \"\",\n" +
				"        \"type\": \"\",\n" +
				"        \"mode\": \"\"\n" +
				"    }\n" +
				"}";
		String jsonLogout = "{\n" +
				"    \"header\": {\"cmd\": \"1001\"},\n" +
				"    \"body\": {\"data\": \"\"}\n" +
				"}";
		String heartBeats = "{\n" +
				"\t\"header\": {\n" +
				"\t\t\"cmd\": \"1002\"\n" +
				"\t},\n" +
				"\t\"body\": {\n" +
				"\t\t\"result\": \"\"\n" +
				"\t}\n" +
				"}";
		//客户端向车载终端发送车机命令
		String Instructions = "{\n" +
				"    \"header\": {\"cmd\": \"1003\"},\n" +
				"    \"body\": {\"msg\": \"<cmd>  <id>   7000  </id>  <param>   180  </param>  <param>   10  </param>  </cmd>\"}\n" +
				"}";
		//请求分组
		String queryGroup = "{\n" +
				"    \"header\": {\"cmd\": \"1008\"},\n" +
				"    \"body\": {\"client\": \"true\"}\n" +
				"}";
		//请求分组下的车辆数据
		String queryGroupCar = "{\n" +
				"    \"header\": {\"cmd\": \"1010\"},\n" +
				"    \"body\": {\"groupid\": \"1\"}\n" +
				"}";

		String json = "{\n" +
				"  \"header\": {\"cmd\": \"1000\"},\n" +
				"  \"body\": {\n" +
				"    \"username\": \"zcxt1\",\n" +
				"    \"password\": \"000000\"\n" +
				"  }\n" +
				"}";
		clientSocket.executeSocket(json);
	}


}
