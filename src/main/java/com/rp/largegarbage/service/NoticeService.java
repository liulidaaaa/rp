package com.rp.largegarbage.service;

import com.rp.largegarbage.entity.Notice;

import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 10:50
 */
public interface NoticeService {
    /**
     * 公告列表(倒序)
     */
    List<Notice> queryNoticeList();

    /**
     * 公告详情
     */
    Notice queryNotice(Integer noticeId);
}
