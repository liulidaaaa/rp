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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    public Boolean signInAndRegister(Integer type, Long phoneNo, String captcha, Long idcard, String username, MultipartFile file, String sessionId) throws Exception {
        if (null==type) {
            return false;
        }
        if (null==phoneNo) {
            return false;
        }
        if (null==captcha) {
            return false;
        }
        if (2==type) {//2-临时申请人
            if (null==idcard) {
                return false;
            }
            //1.校验验证码 成功执行2 失败返回false todo moke

            //2.根据手机号查询临时申请人是否注册过，若注册过，返回true，若未注册，插入用户信息，返回true
            User byPhoneNo = userDao.findByPhoneNo(phoneNo);
            if (null == byPhoneNo) {
                User user = new User();
                user.setPhoneNo(phoneNo);
                user.setIdCard(idcard);
                user.setUsername(username);
                ResponseDTO upload = fileService.upload(file);
                String headerId = (String) upload.getData();
                user.setHeaderId(Integer.parseInt(headerId));
                User save = userDao.save(user);
                //插入用户角色信息
                UserRole userRole = new UserRole();
                userRole.setUserId(save.getUserId());
                userRole.setRoleId(3);//临时申请人
                userRoleDao.saveAndFlush(userRole);
            }else {
                byPhoneNo.setIdCard(idcard);
                userDao.save(byPhoneNo);
            }
            //3.保持用户登录态 前端调用微信接口换取openid和session_key
            //redis.set(sessionId,uuid) 设置时长 拦截器校验uuid非空放行，空重新登陆
            return true;

        } else if (3==type) {//3-发起人

            //校验短信验证码
            //校验发起人
            //1）是否有该用户
            User byPhoneNo = userDao.findByPhoneNo(phoneNo);
            if (null == byPhoneNo) { return false;}
            //判断有无头像和昵称，如果没有，即首次登录，上传微信头像，保存用户昵称
            Integer headerId = byPhoneNo.getHeaderId();
            String username1 = byPhoneNo.getUsername();
            if (null==headerId || null==username1) {
                //文件上传
                ResponseDTO upload = fileService.upload(file);
                String headerId1 = (String) upload.getData();
                //更新
                byPhoneNo.setHeaderId(Integer.parseInt(headerId1));
                byPhoneNo.setUsername(username);
                userDao.save(byPhoneNo);
            }
            //2）该用户是否有发起人角色，若没有，返回false
            List<UserRole> byUserId = userRoleDao.findByUserId(byPhoneNo.getUserId());
            List<String> roleNames = new ArrayList<>();
            for (UserRole userRole : byUserId) {
                Role byRoleId = roleDao.findByRoleId(userRole.getRoleId());
                roleNames.add(byRoleId.getRoleName());
            }
            if (!roleNames.contains("发起人")) {
                return false;
            }
            //缓存登录态
            return true;

        } else if (4==type) {//4-司机

            //校验短信验证码
            //校验发起人手机号是否存在，存在返回true
            User byPhoneNo = userDao.findByPhoneNo(phoneNo);
            if (null == byPhoneNo) { return false;}
            //判断有无头像和昵称，如果没有，即首次登录，上传微信头，保存用户昵称
            Integer headerId = byPhoneNo.getHeaderId();
            String username1 = byPhoneNo.getUsername();
            if (null==headerId || null==username1) {
                //文件上传
                ResponseDTO upload = fileService.upload(file);
                String headerId1 = (String) upload.getData();
                //更新
                byPhoneNo.setHeaderId(Integer.parseInt(headerId1));
                byPhoneNo.setUsername(username);
                userDao.save(byPhoneNo);
            }
            //2）该用户是否是司机
            List<UserRole> byUserId = userRoleDao.findByUserId(byPhoneNo.getUserId());
            List<String> roleNames = new ArrayList<>();
            for (UserRole userRole : byUserId) {
                Role byRoleId = roleDao.findByRoleId(userRole.getRoleId());
                roleNames.add(byRoleId.getRoleName());
            }
            if (!roleNames.contains("司机")) {
                return false;
            }
            //缓存登录态
            return true;

        } else if (5==type) {//5-管理员

            //校验短信验证码
            //校验发起人手机号是否存在，存在返回true
            User byPhoneNo = userDao.findByPhoneNo(phoneNo);
            if (null == byPhoneNo) { return false;}
            //判断有无头像和昵称，如果没有，即首次登录，上传微信头，保存用户昵称
            Integer headerId = byPhoneNo.getHeaderId();
            String username1 = byPhoneNo.getUsername();
            if (null==headerId || null==username1) {
                //文件上传
                ResponseDTO upload = fileService.upload(file);
                String headerId1 = (String) upload.getData();
                //更新
                byPhoneNo.setHeaderId(Integer.parseInt(headerId1));
                byPhoneNo.setUsername(username);
                userDao.save(byPhoneNo);
            }
            //2）该用户是否是管理员
            List<UserRole> byUserId = userRoleDao.findByUserId(byPhoneNo.getUserId());
            List<String> roleNames = new ArrayList<>();
            for (UserRole userRole : byUserId) {
                Role byRoleId = roleDao.findByRoleId(userRole.getRoleId());
                roleNames.add(byRoleId.getRoleName());
            }
            if (!roleNames.contains("管理员")) {
                return false;
            }
            //缓存登录态
            return true;

        } else {//
            return false;
        }
    }

    @Override
    public User queryUserInfo(Long phoneNo) {
        User byPhoneNo = userDao.findByPhoneNo(phoneNo);
        List<Role> roles = new ArrayList<>();
        List<UserRole> byUserId = userRoleDao.findByUserId(byPhoneNo.getUserId());
        for (UserRole userRole : byUserId) {
            Role byRoleId0 = roleDao.findByRoleId(userRole.getRoleId());
            System.out.println(byRoleId0+"===============");
            roles.add(byRoleId0);
        }
        byPhoneNo.setRoles(roles);
        return byPhoneNo;
    }

    @Override
    public User editUserInfo(User user) {
        System.out.println(user);
        User byUserId = userDao.findByUserId(user.getUserId());
        byUserId.setRealName(user.getRealName());
        byUserId.setGender(user.getGender());
        byUserId.setAddress(user.getAddress());
        User save = userDao.save(byUserId);
        return save;
    }

    @Override
    public Boolean uploadHeader(MultipartFile file, Integer userId) throws Exception {
        User user = userDao.findByUserId(userId);
        ResponseDTO upload = fileService.upload(file);
        String headerId = (String) upload.getData();
        user.setHeaderId(Integer.parseInt(headerId));
        userDao.save(user);
        return true;
    }

    @Override
    public Boolean downloadHeader(Integer headerId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileService.downloadFile(headerId,request,response);
        return true;
    }

    @Override
    public Boolean changePhoneNo(Integer userId, Long phoneNoOld, Long phoneNoNew, String verificationCode) {
        User user = userDao.findByUserId(userId);
        Long phoneNo = user.getPhoneNo();
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
