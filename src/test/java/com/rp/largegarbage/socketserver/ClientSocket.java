package com.rp.largegarbage.socketserver;

/**
 * @Description 自定义封装的连接的客户端
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 13:45
 */
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static com.rp.largegarbage.socketserver.SocketHandler.close;
import static com.rp.largegarbage.socketserver.SocketHandler.isSocketClosed;

@Slf4j
@Data
public class ClientSocket implements Runnable{

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String key;
    private String message;

    @Override
    public void run() {
        //每5秒进行一次客户端连接，判断是否需要释放资源
        while (true){
            try {
                TimeUnit.SECONDS.sleep(5);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isSocketClosed(this)){
                log.info("客户端已关闭,其Key值为：{}", this.getKey());
                //关闭对应的服务端资源
                close(this);
                break;
            }
        }
    }
}
