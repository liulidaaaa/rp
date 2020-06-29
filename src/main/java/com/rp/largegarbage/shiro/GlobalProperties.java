package com.rp.largegarbage.shiro;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/24 16:15
 */
@Data
@Component
public class GlobalProperties {
    /** 文件存放路径 */
    @Value("${largegabage.file.path}")
    private String serverPath;

    /** 文件扩展名 */
    @Value("${largegabage.file.extension}")
    private String extension;
}
