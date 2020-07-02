package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.service.UserService;
import com.rp.largegarbage.service.impl.NoticeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:11
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 临时申请人注册并登录
     */
    @PostMapping("visitorSignInAndRegister")
    public ResponseDTO visitorSignInAndRegister(Integer phoneNo, String captcha, Integer idcard, String username, MultipartFile file, String sessionId) throws Exception {
        return ResponseDTO.buildSuccess(userService.visitorSignInAndRegister(phoneNo, captcha, idcard, username, file, sessionId));
    }

    /**
     * 发起人登录
     */
    @PostMapping("initiatorSignIn")
    public ResponseDTO initiatorSignIn(Integer phoneNo, String captcha) {
        return ResponseDTO.buildSuccess(userService.initiatorSignIn(phoneNo, captcha));
    }

    /**
     * 司机登录
     */
    @PostMapping("driverSignIn")
    public ResponseDTO driverSignIn(Integer phoneNo, String captcha) {

        return ResponseDTO.buildSuccess(userService.driverSignIn(phoneNo, captcha));
    }

    /**
     * 管理员登录
     */
    @PostMapping("adminSignIn")
    public ResponseDTO adminSignIn(Integer phoneNo, String captcha) {
        return ResponseDTO.buildSuccess(userService.adminSignIn(phoneNo, captcha));
    }

    /**
     * 编辑用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("editUserInfo")
    public ResponseDTO editUserInfo(User user) {
        return ResponseDTO.buildSuccess(userService.editUserInfo(user));
    }

    /**
     * 用户头像上传 文件地址落库
     */
    @PostMapping("uploadHeader")
    public ResponseDTO uploadHeader(MultipartFile file, Integer userId) throws Exception {
        return ResponseDTO.buildSuccess(userService.uploadHeader(file, userId));
    }

    /**
     * 用户头像下载
     */
    @PostMapping("downloadHeader")
    public ResponseDTO downloadHeader(Integer headerId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseDTO.buildSuccess(userService.downloadHeader(headerId, request, response));
    }

    /**
     * 更换手机号码
     */
    @PostMapping("changePhoneNo")
    public ResponseDTO changePhoneNo(Integer userId, Integer phoneNoOld, Integer phoneNoNew, String verificationCode) {
        return ResponseDTO.buildSuccess(userService.changePhoneNo(userId, phoneNoOld, phoneNoNew, verificationCode));
    }

}
