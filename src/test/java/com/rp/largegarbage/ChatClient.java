package com.rp.largegarbage;

import com.alibaba.fastjson.JSONObject;
import com.rp.largegarbage.dao.CarDao;
import com.rp.largegarbage.dto.CarInfoDTO;
import com.rp.largegarbage.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 13:49
 */
@Slf4j
public class ChatClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatClient.class);

    private static final String host = "119.57.53.254";
    private static final int port = 1502;
    /*private static final String host = "60.29.154.29";
    private static final int port = 7878;*/
     /*private static final String host = "127.0.0.1";
     private static final int port = 8068;*/
    private static Socket socket;
    private static PrintWriter printWriter = null;
    private static BufferedReader bufferedReader = null;
    //private static Date sendHeartPackTime;
    private static Date recieveHeartPackTime;
    @Autowired
    private static CarDao carDao;

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }

    public static void connect() throws IOException {
        //与服务端建立连接
        socket = new Socket(host, port);
        socket.setOOBInline(true);
        //建立连接后获取输出流
        printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new DataOutputStream(socket.getOutputStream()), "UTF-8")));
        bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(socket.getInputStream()), "GBK"));//缓冲区
    }

    public static void recieveMessage() throws IOException {
        //接收服务器推送的数据
        String tmpData = "";
        String temp = null;//临时变量
        while (true) {
            if (!((temp = bufferedReader.readLine()) != null)) {
                break;
            }
            tmpData = doParseResultJson(tmpData, temp);
        }
    }

    /**
     * 按JSON格式进行解析收到的结果，无论是否粘包，都是可进行解析
     * sData：为已经收到的临时数据
     * s：为当前收到的数据
     * 返回结果为未处理的所有数据。
     */
    static String doParseResultJson(String sData, String s) {
        String tmpData = sData + s;
        // 找这个串里有没有相应的JSON符号,没有的话，将数据返回等下一个包
        Boolean bHasJSON = tmpData.contains("{") && tmpData.contains("}}");
        if (!bHasJSON) {
            return tmpData;
        }
        //找有类似JSON串，看"{"是否在"}}"的前面，在前面，则解析，解析失败，则继续找下一个"}}",解析成功，则进行业务处理
        int idxStart = tmpData.indexOf("{");
        int idxEnd = 0;
        while (tmpData.contains("}}")) {
            idxEnd = tmpData.indexOf("}}") + 2;
            //LOGGER.info("{}=>" + idxStart + "--" + idxEnd);
            if (idxStart >= idxEnd) {
                continue;// 找下一个 "}}"
            }
            String sJSON = tmpData.substring(idxStart, idxEnd);
            //LOGGER.info("解析 JSON ...." + sJSON);
            try {
                //解析成功，则说明结束，否则抛出异常，继续接收
                JSONObject jsonObject = JSONObject.parseObject(sJSON);
                ///业务方法
                doCommand(jsonObject);
                //处理完成，则对剩余部分递归解析，直到全部解析完成
                tmpData = tmpData.substring(idxEnd); //剩余未解析部分
                idxEnd = 0; //复位
                if (tmpData.contains("{") && tmpData.contains("}}")) {
                    tmpData = doParseResultJson(tmpData, "");
                    break;
                }
            } catch (Exception e) {
                LOGGER.info("解析 JSON 出错:" + e.getMessage() + " waiting for next '}}'...."); //抛出异常，继续接收，等下一个}
            }
        }
        return tmpData;
    }

    static void doCommand(JSONObject jsonObject) throws IOException {
        //解析 header body
        String header = jsonObject.get("header").toString();
        String body = jsonObject.get("body").toString();
        JSONObject oHeader = JSONObject.parseObject(header);
        String cmd = oHeader.get("cmd").toString();
        LOGGER.info("解析 JSON OK : cmd=" + cmd + " body=" + body);
        switch (cmd) {
            case "8000":
                JSONObject oBody = JSONObject.parseObject(body);
                String cmd1 = oBody.get("cmd").toString();
                String result = oBody.get("result").toString();
                switch (cmd1) {
                    case "1000":
                        //收到登录信息
                        if ("succeed".equals(result)) {
                            LOGGER.info("登陆成功");
                            //发送心跳
                            heartbeats();
                            LOGGER.info("发送心跳");
                            //new Client("119.57.53.254", 1502).start();
                            //new Client("127.0.0.1", 8085).start();
                            //客户端请求服务器下发用户权限下客户及分组数据
                            group();
                            LOGGER.info("客户及分组");
                            //监听心跳返回，若没有重新连接
                            getHeartbeats();
                        } else {
                            LOGGER.info("异常！未登陆成功");
                            throw new RuntimeException("异常！未登陆成功");
                        }
                        break;
                    case "1002":
                        //收到心跳返回，记录时间
                        recieveHeartPackTime = new Date();
                        LOGGER.info("返回心跳 " + recieveHeartPackTime);
                        break;
                    default:
                }
                break;
            case "1009":
                //收到的分组信息
                JSONObject oBody1 = JSONObject.parseObject(body);
                String groupId = oBody1.get("groupid").toString();
                //客户端请求服务器下发用户权限下分组的车辆数据
                cars(groupId);
                LOGGER.info("分组下车辆");
                break;
            case "1011":
                //服务器向客户端下发用户权限下指定分组的车辆静态数据
                break;
            case "1007":
                JSONObject oBody2 = JSONObject.parseObject(body);
                updateCar(oBody2);
                break;
            default:
        }
    }

    static void updateCar(JSONObject oBody2) {
        executorService.submit(() -> {
            CarInfoDTO tt = (CarInfoDTO) JSONObject.toJavaObject(oBody2, CarInfoDTO.class);
            //LOGGER.info("车辆信息CarInfoDTO=" + tt);
            String gpstime = tt.getGpstime();//获取的轨迹时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date gpsdatetime = null;
            Boolean flag_start = true;
            try {
                gpsdatetime = sdf.parse(gpstime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date nowDate = new Date();//当前时间
            long l = (nowDate.getTime() - gpsdatetime.getTime()) / (1000);
            if (l > 300 || l < -300) {
                //时间超过300秒
                flag_start = false;
            }
            if (flag_start && tt.getAv() == "1") {
                String code = tt.getCode();//车牌号
                //合理的数据则更新 ,有效的定位
                Car car = carDao.findByCarCode(code);
                car.setLng(Double.parseDouble(tt.getLng()));
                car.setLat(Double.parseDouble(tt.getLat()));
                car.setVeo(tt.getVeo());
                car.setDir(tt.getDir());
                Car save = carDao.save(car);
                LOGGER.info("编号为" + tt.getCode() + "的车数据更新成功" + save);
            }
        });
    }

    private static ScheduledExecutorService scheduledExecutorService = newScheduledThreadPool(2);

    public static void heartbeats() {
        //登陆成功发送心跳 线程池 提交任务
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                /*Socket o= new Socket("127.0.0.1", 8085);
                //建立连接后获取输出流
                PrintWriter p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new DataOutputStream(o.getOutputStream()), "UTF-8")));
                BufferedReader b = new BufferedReader(new InputStreamReader(new DataInputStream(o.getInputStream()), "UTF-8"));*/
                String cmd1002 = "{ \n" +
                        "\"header\": { \n" +
                        " \"cmd\":\"1002\" \n" +
                        "}, \n" +
                        "\"body\": \n" +
                        "{ \n" +
                        " \"result\":\"\" \n" +
                        "} \n" +
                        "} ";
                printWriter.println(cmd1002);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    private static ExecutorService executorService = newFixedThreadPool(4);

    public static void getHeartbeats() {
        //2分钟没有接收到心跳包的返回，重连
        Future<Object> submit = executorService.submit(() -> {
            while (true) {
                //判断当前时间和上一次接收心跳包的时间差
                Date nowDate2 = new Date();
                if (null != recieveHeartPackTime) {
                    long l = (nowDate2.getTime() - recieveHeartPackTime.getTime()) / (1000 * 60);
                    if (l > 1) {
                        //心跳异常，判断是否有连接问题，未连接重连，未登录，重新登陆
                        if (!socket.isConnected()) {
                            try {//2分钟没有接收到心跳包的返回,连接断开
                                connect();
                                LOGGER.info("2分钟没有接收到心跳包的返回,连接断开重新连接");
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            if (socket.isConnected()) {
                                login();
                                LOGGER.info("连接断开，重新登录");
                                try {
                                    recieveMessage();
                                    LOGGER.info("连接断开，重新接收消息");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            login();
                            LOGGER.info("连接未断开重新登录");
                            try {
                                recieveMessage();
                                LOGGER.info("连接未断开重新接收消息");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        //LOGGER.info("心跳正常");
                    }
                }
            }
        });
    }

    public static void login() {
        /*String cmd1000 = "{\n" +
                "\"header\":\n" +
                "{\n" +
                "\"cmd\":\"1000\"\n" +
                "},\n" +
                "\"body\":\n" +
                "{\n" +
                "\"name\":\"zcxt1\",\n" +
                "\"pass\":\"000000\",\n" +
                "\"ver\":\"4.6936\",\n" +
                "\"type\":\"json_common\",\n" +
                "\"mode\":\"\"\n" +
                "}\n" +
                "}";*/
        String cmd1000 = "{\n" +
                "\"header\":\n" +
                "{\n" +
                "\"cmd\":\"1000\"\n" +
                "},\n" +
                "\"body\":\n" +
                "{\n" +
                "\"name\":\"admintest\",\n" +
                "\"pass\":\"000000\",\n" +
                "\"ver\":\"4.6936\",\n" +
                "\"type\":\"json_common\",\n" +
                "\"mode\":\"\"\n" +
                "}\n" +
                "}";
        printWriter.println(cmd1000);
        printWriter.flush();
    }

    public static void group() {
        String cmd1008 = "{ \n" +
                "\"header\": \n" +
                "{ \n" +
                " \"cmd\":\"1008\" \n" +
                "}, \n" +
                "\"body\": \n" +
                "{ \n" +
                " \"client\":\"true\" \n" +
                "} \n" +
                "} ";
        printWriter.print(cmd1008);
        printWriter.flush();
    }

    public static void cars(String groupId) {
        String cmd1010 = "{ \n" +
                "\"header\": \n" +
                "{ \n" +
                " \"cmd\":\"1010\" \n" +
                "}, \n" +
                "\"body\": \n" +
                "{ \n" +
                " \"groupid\":\"" + groupId + "\" \n" +
                "} \n" +
                "} ";
        printWriter.println(cmd1010);
        printWriter.flush();
    }

    public static void main(String[] args) throws IOException {
        ChatClient.connect();
        LOGGER.info("当前连接状态isConnected= " + socket.isConnected());
        if (socket.isConnected()) {
            ChatClient.login();
            LOGGER.info("请求登录 。。。");
            recieveMessage();

        }
    }


}
