package com.rp.largegarbage.socket;
import java.util.Timer;
import java.util.Date;
import java.util.TimerTask;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/3 10:10
 */
public class HeartTimer {
    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("发送心跳task  run:"+ new Date());
                String cmd1002 = "{ \n" +
                        "\"header\": { \n" +
                        " \"cmd\":\"1002\" \n" +
                        "}, \n" +
                        "\"body\": \n" +
                        "{ \n" +
                        " \"result\":\"\" \n" +
                        "} \n" +
                        "} ";
                //SocketHandler.sendMessage(clientSocket,cmd1002);
            }
        };
        Timer t = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每30秒执行一次
        t.schedule(timerTask,10,30000);
    }
}
