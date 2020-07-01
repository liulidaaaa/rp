package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.dto.LoginDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: largegarbage
 * @description: 控制器
 * @author: lld
 * @create: 2020-06-18 17:50
 **/
@RestController
@RequestMapping("pub")
public class PublicController {

    @RequestMapping("need_login")
    public ResponseDTO needLogin() {
        return ResponseDTO.buildSuccess("温馨提示：请使用对应的账号登录", -2);
    }

    @RequestMapping("not_permit")
    public ResponseDTO notPermit() {
        return ResponseDTO.buildSuccess("温馨提示：拒绝访问，没权限", -3);
    }


    @RequestMapping("index")
    public ResponseDTO index() {
        List<String> videoList = new ArrayList<>();
        videoList.add("XXXXXXX");
        return ResponseDTO.buildSuccess(videoList);
    }

    /**
     * 登录接口
     *
     * @param loginDTO
     * @param request
     * @param response
     * @return
     */
    @PostMapping("login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> info = new HashMap<>();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginDTO.getUsername(), loginDTO.getPassword());
            subject.login(usernamePasswordToken);
            info.put("msg", "登录成功");
            info.put("session_id", subject.getSession().getId());
            return ResponseDTO.buildSuccess(info);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDTO.buildError("账号或者密码错误");
        }
    }

    @GetMapping("/logout")
    public ResponseDTO findMyPlayRecord() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
        }
        SecurityUtils.getSubject().logout();
        return ResponseDTO.buildSuccess("logout成功");
    }

}
