package com.rp.largegarbage.nettyclient;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 21:52
 */
@Data
public class CustomProtocol implements Serializable {

    private long id ;
    private String content ;
    public CustomProtocol() {
    }
    public CustomProtocol(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
