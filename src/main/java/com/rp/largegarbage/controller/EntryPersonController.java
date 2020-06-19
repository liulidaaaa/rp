package com.rp.largegarbage.controller;

import com.rp.largegarbage.dao.EntryPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: largegarbage
 * @description: 录入人员控制器
 * @author: lld
 * @create: 2020-06-18 17:50
 **/
@RestController
public class EntryPersonController {
    @Autowired
    private EntryPersonRepository entryPersonRepository;

    /**
     * 录入人员登录
     * @return
     */
    @GetMapping("/entryPerson/login")
    public Long entryPersonLogin() {
        return entryPersonRepository.count();
    }


}
