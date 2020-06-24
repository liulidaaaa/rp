package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/24 16:18
 */
public interface FileInfoDao extends JpaRepository<FileInfo, Integer> {
}
