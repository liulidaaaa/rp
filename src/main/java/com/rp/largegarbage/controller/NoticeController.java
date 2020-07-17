package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.Notice;
import com.rp.largegarbage.service.NoticeService;
import com.rp.largegarbage.service.impl.NoticeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:36
 */
@RestController
@RequestMapping("notice")
public class NoticeController {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    /**
     * 创建通知公告
     * @param notice
     * @return
     */
    @PostMapping("createNotice")
    public ResponseDTO createNotice(@RequestBody Notice notice) {
        return ResponseDTO.buildSuccess(noticeService.createNotice(notice),"创建成功");
    }

    /**
     * 公告列表(倒序)
     */
    @GetMapping("queryNoticeList")
    public ResponseDTO queryNoticeList() {
        return ResponseDTO.buildSuccess(noticeService.queryNoticeList());
    }

    /**
     * 公告详情
     */
    @GetMapping("queryNoticeInfo")
    public ResponseDTO queryNoticeInfo(Integer noticeId) {
        return ResponseDTO.buildSuccess(noticeService.queryNotice(noticeId));
    }
}
