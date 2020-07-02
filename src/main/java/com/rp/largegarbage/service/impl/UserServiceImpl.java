package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.RoleDao;
import com.rp.largegarbage.dao.UserDao;
import com.rp.largegarbage.dao.UserRoleDao;
import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.Role;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.entity.UserRole;
import com.rp.largegarbage.service.FileService;
import com.rp.largegarbage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 11:04
 */
@Service
public class UserServiceImpl implements UserService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private FileService fileService;

    @Override
    public Boolean visitorSignInAndRegister(Integer phoneNo, String captcha, Integer idcard, String username,MultipartFile file, String sessionId) throws Exception {
        //1.校验验证码 成功执行2 失败返回false todo moke

        //2.根据手机号查询临时申请人是否注册过，若注册过，返回true，若未注册，插入用户信息，返回true
        User byPhoneNo = userDao.findByPhoneNo(phoneNo);
        if (null == byPhoneNo) {
            User user = new User();
            user.setPhoneNo(phoneNo);
            user.setIdCard(idcard);
            user.setUsername(username);
            ResponseDTO upload = fileService.upload(file);
            Integer headerId = (Integer) upload.getData();
            user.setHeaderId(headerId);
            User save = userDao.save(user);
            //todo moke 插入用户角色信息
            UserRole userRole = new UserRole();
            userRole.setUserId(save.getUserId());
            userRole.setRoleId(3);
            userRoleDao.save(userRole);
        }
        //3.保持用户登录态 前端调用微信接口换取openid和session_key
        //redis.set(sessionId,uuid) 设置时长 拦截器校验uuid非空放行，空重新登陆
        return true;
    }

    @Override
    public Boolean initiatorSignIn(Integer phoneNo, String captcha) {
        //校验短信验证码
        //校验发起人手机号是否存在，存在返回true
        User byPhoneNo = userDao.findByPhoneNo(phoneNo);
        if (null == byPhoneNo) { return false;}
        //缓存登录态
        return true;
    }

    @Override
    public Boolean driverSignIn(Integer phoneNo, String captcha) {
        //校验短信验证码
        //校验发起人手机号是否存在，存在返回true
        User byPhoneNo = userDao.findByPhoneNo(phoneNo);
        if (null == byPhoneNo) { return false;}
        //缓存登录态
        return true;
    }

    @Override
    public Boolean adminSignIn(Integer phoneNo, String captcha) {
        //校验短信验证码
        //校验发起人手机号是否存在，存在返回true
        User byPhoneNo = userDao.findByPhoneNo(phoneNo);
        if (null == byPhoneNo) { return false;}
        //缓存登录态
        return true;
    }

    @Override
    public User editUserInfo(User user) {
        User byUserId = userDao.findByUserId(user.getUserId());
        byUserId.setRealName(user.getRealName());
        byUserId.setGender(user.getGender());
        byUserId.setPhoneNo(user.getPhoneNo());
        byUserId.setAddress(user.getAddress());
        User save = userDao.save(byUserId);
        return save;
    }

    @Override
    public Boolean uploadHeader(MultipartFile file, Integer userId) throws Exception {
        User user = userDao.findByUserId(userId);
        ResponseDTO upload = fileService.upload(file);
        Integer headerId = (Integer) upload.getData();
        user.setHeaderId(headerId);
        userDao.save(user);
        return true;
    }

    @Override
    public Boolean downloadHeader(Integer headerId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileService.downloadFile(headerId,request,response);
        return true;
    }

    @Override
    public Boolean changePhoneNo(Integer userId, Integer phoneNoOld, Integer phoneNoNew, String verificationCode) {
        User user = userDao.findByUserId(userId);
        Integer phoneNo = user.getPhoneNo();
        if (phoneNo.equals(phoneNoOld)) {
            return false;
        }
        user.setPhoneNo(phoneNoNew);
        userDao.save(user);
        return true;
    }

    @Override
    public Role roleList(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

}
