package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.dao.NoticeDao;
import com.rp.largegarbage.entity.Notice;
import com.rp.largegarbage.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 10:54
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);


    @Autowired
    private NoticeDao noticeDao;

    @Override
    public List<Notice> queryNoticeList() {
        return noticeDao.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
    }

    @Override
    public Notice queryNotice(Integer noticeId) {
        //查询详情
        Optional<Notice> byId = noticeDao.findById(noticeId);
        Notice notice = byId.get();
        //浏览量加一
        notice.setPv(notice.getPv()+1);
        return notice;
    }
}
