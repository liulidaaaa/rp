package com.rp.largegarbage.socket;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 13:43
 */


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static com.rp.largegarbage.socket.SocketHandler.register;

@Slf4j
@Data
@Component
@PropertySource("classpath:socket.properties")
@NoArgsConstructor
public class SocketServer {

    @Value("${port}")
    private Integer port;
    private boolean started;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);


    public static void main(String[] args){
        new SocketServer().start(8068);
    }

    public void start(){
        start(null);
    }
   /* @Autowired
    private UserService userService;//测试使用*/


    public void start(Integer port){
        log.info("port: {}, {}", this.port, port);
        try {
            serverSocket =  new ServerSocket(port == null ? this.port : port);
            started = true;
            log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }

        while (started){
            try {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                ClientSocket register = register(socket);
                log.info("客户端已连接，其Key值为：{}", register.getKey());
                /*List<User> list = userService.queryEntityListAll();
                SocketHandler.sendMessage(register, JSONUtil.toJson(list));*/
                SocketHandler.sendMessage(register,
                        //服务器下发登陆成功
                        /*"{ \n" +
                        "\"header\": \n" +
                        "{ \n" +
                        " \"cmd\":\"8000\" \n" +
                        "}, \n" +
                        "\"body\": \n" +
                        "{ \n" +
                        " \"cmd\":\"1000\", \n" +
                        " \"result\":\"success\" \n" +
                        "} \n" +
                        "} ");*/
                        //服务器向客户端下发用户的客户、分组数据
                        "{ \n" +
                                "\"header\": \n" +
                                "{ \n" +
                                " \"cmd\":\"1009\" \n" +
                                "}, \n" +
                                "\"body\": \n" +
                                "{ \n" +
                                " \"groupid\":\"\", \n" +
                                " \"relation\":\"\", \n" +
                                " \"customer\":\"\", \n" +
                                " \"goupname\":\"\" \n" +
                                "} \n" +
                                "} ");

                if (register != null){
                    executorService.submit(register);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
