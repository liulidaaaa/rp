package com.rp.largegarbage;

import com.alibaba.fastjson.JSONObject;
import com.rp.largegarbage.dao.CarDao;
import com.rp.largegarbage.dao.OrderGarDao;
import com.rp.largegarbage.dao.RoleDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.entity.Car;
import com.rp.largegarbage.entity.Role;
import com.rp.largegarbage.entity.User;
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
	void testRoleDao() {
		//orderDao.updateOrderStatusByOrderId(1,1);
		/*List<Integer> ids = new ArrayList();
		ids.add(1);
		orderDao.deleteByIds(ids);*/
		System.out.println(roleDao.findByRoleName("录入人员"));
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
	void testJson(){
		String sJSON = "{ \n" +
				"\"header\": \n" +
				"{ \n" +
				" \"cmd\":\"1001\" \n" +
				"}, \n" +
				"\"body\": \n" +
				"{ \n" +
				" \"data\":\"\" \n" +
				"} \n" +
				"} ";
		JSONObject jsonObject = JSONObject.parseObject(sJSON);
		//解析 header body
		String header = jsonObject.get("header").toString();
		String body = jsonObject.get("body").toString();
		JSONObject oHeader = JSONObject.parseObject(header);
		String cmd = oHeader.get("cmd").toString();
		//JSONObject oBody = JSONObject.parseObject(body);
		System.out.println(cmd);
		System.out.println(body);
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

	@Test
	void doParseResultJson(){
		String tmpData = "{ \n" +
				"\"header\": \n" +
				"{ \n" +
				" \"cmd\":\"1001\" \n" +
				"}, \n" +
				"\"body\": \n" +
				"{ \n" +
				" \"data\":\"\" \n" +
				"}}{ \n" +
				"\"header\": \n" +
				"{ \n" +
				" \"cmd\":\"1001\" \n" +
				"}, \n" +
				"\"body\": \n" +
				"{ \n" +
				" \"data\":\"\" \n" +
				"}}  ";
		Boolean bHasJSON = tmpData.contains("{") && tmpData.contains("}}");
		//在前面，则解析，解析失败，则继续找下一个"}"
		//解析成功，则进行业务处理
		//处理完成，则对剩余部分递归解析，直到全部解析完成（此项一般用不到，仅适用于一次发两个以上的JSON串才需要，
		//每次只传一个JSON串的情况下，是不需要的
		int idxStart = tmpData.indexOf("{");
		int idxEnd = 0;
		while (tmpData.contains("}}")) {
			idxEnd = tmpData.indexOf("}}") + 2;
			System.out.println("{}=>" + idxStart + "--" + idxEnd);
			if (idxStart >= idxEnd) {
				continue;// 找下一个 "}"
			}
			String sJSON = tmpData.substring(idxStart, idxEnd);
			System.out.println("解析 JSON ...." + sJSON);
			//解析成功，则说明结束，否则抛出异常，继续接收
			JSONObject jsonObject = JSONObject.parseObject(sJSON);
			System.out.println(jsonObject.toJSONString());
			tmpData = tmpData.substring(idxEnd); //剩余未解析部分
			idxEnd = 0; //复位
			if (tmpData.contains("{") && tmpData.contains("}}")) {
				//tmpData = doParseResultJson(tmpData, "");
				System.out.println(jsonObject.toJSONString());
				break;
			}
		}

	}
}
