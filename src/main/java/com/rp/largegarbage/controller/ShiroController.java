package com.rp.largegarbage.controller;

import com.rp.largegarbage.dao.EntryPersonRepository;
import com.rp.largegarbage.dto.LoginDTO;
import com.rp.largegarbage.entity.User;
import com.rp.largegarbage.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: largegarbage
 * @description: 录入人员控制器
 * @author: lld
 * @create: 2020-06-18 17:50
 **/
@RestController
public class ShiroController {


    @Autowired
    private ShiroService shiroService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "/index";
    }

    @PostMapping("/rp/garbage/login")
    public Map<String, Object> login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
        //@Validated进行验证，BindingResult可以获取校验错误信息
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("status", 400);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return result;
        }

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        //用户信息
        User user = shiroService.findByUsername(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(password)) {
            result.put("status", 400);
            result.put("msg", "账号或密码有误");
        } else {
            //生成token，并保存到数据库
            result = shiroService.createToken(user.getUserId());
            result.put("status", 200);
            result.put("msg", "登陆成功");
        }
        return result;
    }

    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public Map<String, Object> logout(@RequestHeader("token")String token) {
        Map<String, Object> result = new HashMap<>();
        shiroService.logout(token);
        result.put("status", 200);
        result.put("msg", "您已安全退出系统");
        return result;
    }


}
