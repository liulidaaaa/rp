package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.Role;
import com.rp.largegarbage.entity.User;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 发布人，司机，管理员信息为后台手动维护，无注册
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 10:56
 */
public interface UserService {
    /**
     * 临时申请人注册并登录/发起人登录/司机登录/管理员登录
     * type: 2-临时申请人，3-发起人，4-司机，5-管理员
     */
    Boolean signInAndRegister(Integer type, Long phoneNo, String captcha, Long idcard, String username, MultipartFile file, String sessionId) throws Exception;

    /**
     * 查询用户信息
     *
     * @param phoneNo
     * @return
     */
    User queryUserInfo(Long phoneNo);

    /**
     * 编辑用户信息
     *
     * @param user
     * @return
     */
    User editUserInfo(User user);

    /**
     * 用户头像上传 文件地址落库
     */
    Boolean uploadHeader(MultipartFile file, Integer userId) throws Exception;
    /**
     * 用户头像下载
     */
    Boolean downloadHeader(Integer headerId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 更换手机号码
     */
    Boolean changePhoneNo(Integer userId, Long phoneNoOld, Long phoneNoNew, String verificationCode) ;

    /**
     * 各种角色列表（发起人/司机/管理员）列表
     */
    Role roleList(String rolename);


}
