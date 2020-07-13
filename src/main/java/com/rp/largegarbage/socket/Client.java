package com.rp.largegarbage.socket;


import com.alibaba.fastjson.JSONObject;
import com.rp.largegarbage.dao.CarDao;
import com.rp.largegarbage.dto.CarInfoDTO;
import com.rp.largegarbage.entity.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 心跳
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/9 10:51
 */
public class Client {

    /**
     * 处理服务端发回的对象，可实现该接口。
     */
    public static interface ObjectAction{
        void doAction(Object obj,Client client);
    }

    public static final class DefaultObjectAction implements ObjectAction{
        @Override
        public void doAction(Object obj, Client client) {
            System.out.println("处理：\t"+obj.toString());
        }
    }


    public static void main(String[] args) throws UnknownHostException, IOException {
        /*String serverIp = "127.0.0.1";
        int port = 8085;*/
        String serverIp = "119.57.53.254";
        int port = 1502;
        Client client = new Client(serverIp,port);
        client.start();
    }

    private String serverIp;
    private int port;
    private Socket socket;
    private BufferedReader bf;
    private PrintWriter pw;
    private boolean running=false; //连接状态

    private long lastSendTime; //最后一次发送数据的时间

    //用于保存接收消息对象类型及该类型消息处理的对象
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class,ObjectAction>();

    public Client(String serverIp, int port) {
        this.serverIp=serverIp;
        this.port=port;
    }

    public void start() throws UnknownHostException, IOException {
        if(running) {
            return;
        }
        socket = new Socket(serverIp,port);
        System.out.println("本地端口："+socket.getLocalPort());
        lastSendTime=System.currentTimeMillis();
        running=true;
        System.out.println(socket.isConnected());
        Client.this.sendJson("{\n" +
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
                "}");//登录
        new Thread(new KeepAliveWatchDog()).start();  //保持长连接的线程，每隔2秒项服务器发一个一个保持连接的心跳消息
        new Thread(new ReceiveWatchDog()).start();    //接受消息的线程，处理消息
    }

    public void stop(){
        if(running) {
            running=false;
        }
    }

    /**
     * 添加接收对象的处理对象。
     * @param cls 待处理的对象，其所属的类。
     * @param action 处理过程对象。
     */
    public void addActionMap(Class<Object> cls,ObjectAction action){
        actionMapping.put(cls, action);
    }

    public void sendJson(String obj) throws IOException {
        PrintWriter oos = new PrintWriter(new OutputStreamWriter(new DataOutputStream(socket.getOutputStream()),"UTF-8"));
        oos.println(obj);
        System.out.println("发送：\t"+obj);
        oos.flush();
    }

    class KeepAliveWatchDog implements Runnable{
        long checkDelay = 10;
        long keepAliveDelay = 4000;

        @Override
        public void run() {
            while(running){
                if(System.currentTimeMillis()-lastSendTime>keepAliveDelay){
                    try {
                        Client.this.sendJson("{\"header\": {\"cmd\":\"1002\"}, \"body\":{\"result\":\"\"}} ");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                }else{
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }
    }

    class ReceiveWatchDog implements Runnable{

        /** logger */
        private final Logger LOGGER = LoggerFactory.getLogger(ReceiveWatchDog.class);

        @Autowired
        private CarDao carDao;

        String doParseResultJson(String sData, String s) {
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
        void doCommand(JSONObject jsonObject) throws IOException {
            //解析 header body
            PrintWriter oos = new PrintWriter(new OutputStreamWriter(new DataOutputStream(socket.getOutputStream()),"UTF-8"));
            String header = jsonObject.get("header").toString();
            String body = jsonObject.get("body").toString();
            JSONObject oHeader = JSONObject.parseObject(header);
            String cmd = oHeader.get("cmd").toString();
            System.out.println("解析 JSON OK : cmd=" + cmd + " body=" + body);
            switch (cmd) {
                case "8000":
                    JSONObject oBody = JSONObject.parseObject(body);
                    String cmd1 = oBody.get("cmd").toString();
                    String result = oBody.get("result").toString();
                    switch (cmd1) {
                        case "1000":
                            //收到登录信息
                            if ("succeed".equals(result)) {
                                System.out.println("登陆成功");
                                //发送心跳
                                //heartbeats();
                                System.out.println("发送心跳");
                                //new Client("119.57.53.254", 1502).start();
                                //new Client("127.0.0.1", 8085).start();
                                //客户端请求服务器下发用户权限下客户及分组数据
                                //group();
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
                                oos.print(cmd1008);
                                oos.flush();
                                System.out.println("客户及分组");
                                //监听心跳返回，若没有重新连接
                                //getHeartbeats();
                            } else {
                                System.out.println("异常！未登陆成功");
                                throw new RuntimeException("异常！未登陆成功");
                            }
                            break;
                        case "1002":
                            //收到心跳返回，记录时间
                            //recieveHeartPackTime = new Date();
                            //LOGGER.info("返回心跳 " + recieveHeartPackTime);
                            break;
                        default:
                    }
                    break;
                case "1009":
                    //收到的分组信息
                    JSONObject oBody1 = JSONObject.parseObject(body);
                    String groupId = oBody1.get("groupid").toString();
                    //客户端请求服务器下发用户权限下分组的车辆数据
                    //cars(groupId);
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
                    oos.println(cmd1010);
                    oos.flush();
                    System.out.println("分组下车辆");
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
        void updateCar(JSONObject oBody2) {
            //executorService.submit(() -> {
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
                    System.out.println("编号为" + tt.getCode() + "的车数据更新成功" + save);
                }
         //   });
        }
        /*public static void group() {
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
        }*/
        @Override
        public void run() {
            while(running){
                try {
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    if(in.available()>0){
                        BufferedReader bf = new BufferedReader(new InputStreamReader(new DataInputStream(in),"GBK"));
                        PrintWriter pw= new PrintWriter(new BufferedWriter(new OutputStreamWriter(new DataOutputStream(out), "UTF-8")));

                        String tmpData = "";
                        String temp = null;//临时变量
                        while (true) {
                            if (!((temp = bf.readLine()) != null)) {
                                break;
                            }
                            tmpData = doParseResultJson(tmpData, temp);
                        }
                        /*ObjectAction oa = actionMapping.get(obj.getClass());
                        oa = oa==null?new DefaultObjectAction():oa;
                        oa.doAction(obj, Client.this);*/

                    }else{
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }
        }
    }

}
