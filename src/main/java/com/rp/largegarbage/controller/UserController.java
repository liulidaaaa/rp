package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:11
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 登录
     * 临时申请人注册并登录/发起人登录/司机登录/管理员登录
     * type: 2-临时申请人，3-发起人，4-司机，5-管理员
     */
    @PostMapping("signInAndRegister")
    public ResponseDTO signInAndRegister(Integer type, Long phoneNo, String captcha, Long idcard, String username, MultipartFile file, String sessionId) throws Exception {
        Boolean aBoolean = userService.signInAndRegister(type, phoneNo, captcha, idcard, username, file, sessionId);
        if (aBoolean) {
            return ResponseDTO.buildSuccess(userService.queryUserInfo(phoneNo),"登陆成功");
        }else {return ResponseDTO.buildSuccess("登录信息不正确，请重新登陆");}
    }

    /**
     * 编辑用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("editUserInfo")
    public ResponseDTO editUserInfo(@RequestBody User user) {
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
    public ResponseDTO changePhoneNo(Integer userId, Long phoneNoOld, Long phoneNoNew, String verificationCode) {
        return ResponseDTO.buildSuccess(userService.changePhoneNo(userId, phoneNoOld, phoneNoNew, verificationCode));
    }
}
