package com.rp.largegarbage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 维持连接的消息对象（心跳对象）
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/9 10:41
 */
public class KeepAlive implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    /*
     * 覆盖该方法，仅用于测试使用。
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t维持连接包";
    }
}
