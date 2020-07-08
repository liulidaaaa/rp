//package com.rp.largegarbage.service.impl;
//
///**
// * @Description
// * @Author liulida <2979284403@qq.com>
// * @Version v1.0.0
// * @Since 1.0
// * @Date 2020/7/1 17:58
// */
//using DAL;
//        using Model;
//        using Newtonsoft.Json;
//        using Newtonsoft.Json.Linq;
//        using System;
//        using System.Net;
//        using System.Net.Sockets;
//        using System.Text;
//
//        namespace BLL
//        {
//public class BllManager
//{
//    Socket clientSocket;
//    DataHandler dataHandler;
//    byte[] data = new byte[1024];
//    DealDataDB dataDB;
//    System.Timers.Timer heartTimer;
//    DateTime heartPackTime;
//    public ServerInfo serverInfo { get; set; }
//    public UserInfo userInfo { get; set; }
//    private static log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
//    public BllManager()
//    {
//        clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
//        dataHandler = new DataHandler();
//        dataDB = new DealDataDB();
//    }
//    //// <summary>
//    ///创建连接
//    /// </summary>
//    /// <returns></returns>
//    public bool CreateConnect(ServerInfo serverInfo, out string msg)
//    {
//
//        try
//        {
//
//            IPAddress IP = serverInfo.IPAdressSample(serverInfo.IP, out msg);
//            IPEndPoint endPoint = new IPEndPoint(IP, Convert.ToInt32(serverInfo.Port));
//            clientSocket.Connect(endPoint);
//
//            //③实现异步接受消息的方法 客户端不断监听消息
//            clientSocket.BeginReceive(data, 0, data.Length, SocketFlags.None, new AsyncCallback(ReceiveMessage), clientSocket);
//
//        }
//        catch (Exception e)
//        {
//            msg = e.Message;
//            log.Error("连接异常：" + e.Message);
//            return false;
//        }
//        return true;
//
//    }
//
//        #region 接收信息
//    /// <summary>
//    /// 接收信息
//    /// </summary>
//    /// <param name="ar"></param>
//    public void ReceiveMessage(IAsyncResult ar)
//    {
//        try
//        {
//            var socket = ar.AsyncState as Socket;
//
//            //方法参考：http://msdn.microsoft.com/zh-cn/library/system.net.sockets.socket.endreceive.aspx
//            var length = socket.EndReceive(ar);
//            //读取出来消息内容
//            var message = System.Text.Encoding.Default.GetString(data,0, length) ; //Encoding.UTF8.GetString(data, 0, length);
//            string[] dataList = dataHandler.GetActualString(message);
//            if (dataList.Length > 0)
//            {
//                foreach (string msg in dataList)
//                {
//                    JObject jo = (JObject)JsonConvert.DeserializeObject(msg);
//                    string head = jo["header"].ToString();
//                    string body = jo["body"].ToString();
//                    JObject jo2 = (JObject)JsonConvert.DeserializeObject(body);
//                    if (head.Contains("1009"))//收到的分组信息
//                    {
//                        log.Info("接收到分组返回1009信息:" + message + "-----" + DateTime.Now.ToLocalTime());
//                        string groupId = jo2["groupid"].ToString();
//                        //客户端请求服务器下发用户权限下分组的车辆数据
//
//                        string cmd_1010 = Cmd.Cmd_1010(groupId);
//                        byte[] sendData = System.Text.Encoding.Default.GetBytes(cmd_1010);
//
//                        clientSocket.BeginSend(sendData, 0, sendData.Length, SocketFlags.None, null, null);
//
//                        //System.Timers.Timer tt = new System.Timers.Timer(10000);//实例化Timer类，设置间隔时间为30000毫秒；
//
//                        //// tt.Elapsed += new System.Timers.ElapsedEventHandler(SendCmd_1010);//到达时间的时候执行事件；
//                        //tt.Elapsed += new System.Timers.ElapsedEventHandler((s, e) => SendCmd_1010(s, e, groupId));
//
//                        //tt.AutoReset = true;//设置是执行一次（false）还是一直执行(true)；
//
//                        //tt.Enabled = true;//是否执行System.Timers.Timer.Elapsed事件；
//                    }
//                    else if (head.Contains("8000"))
//                    {
//                        string cmd = jo2["cmd"].ToString();
//                        string result = jo2["result"].ToString();
//                        if (cmd == "1000" && result == "succeed")//登录指令
//                        {
//                            log.Info("接收到登录指令1000返回信息:" + message + "-----" + DateTime.Now.ToLocalTime());
//                            //登录成功 发送心跳包
//                            if (heartTimer == null)
//                            {
//                                heartTimer = new System.Timers.Timer(30000);//实例化Timer类，设置间隔时间为30000毫秒；
//
//                                heartTimer.Elapsed += new System.Timers.ElapsedEventHandler(HeartBucket);//到达时间的时候执行事件；
//
//                                heartTimer.AutoReset = true;//设置是执行一次（false）还是一直执行(true)；
//
//                                heartTimer.Enabled = true;//是否执行System.Timers.Timer.Elapsed事件；
//                            }
//                            else
//                            {
//                                if(!heartTimer.Enabled)
//                                    heartTimer.Enabled = true;
//                            }
//
//                            ////////////////////////
//                            ///登录成功 发送 客户端请求服务器下发用户权限下客户及分组数据
//                            string cmd1008 = Cmd.Cmd_1008();
//                            byte[] sendData = System.Text.Encoding.Default.GetBytes(cmd1008);
//
//                            clientSocket.BeginSend(sendData, 0, sendData.Length, SocketFlags.None, null, null);
//                        }
//                        else if (cmd == "1002")//心跳包返回的状态码
//                        {
//                            log.Info("接收到心跳包返回的状态码信息1002:" + message+"-----" +DateTime.Now.ToLocalTime());
//                            heartPackTime = DateTime.Now.ToLocalTime();//如果收到心跳包返回码，就把当前时间设为心跳包返回码时间
//                        }
//                    }
//                    else if (head.Contains("1011"))
//                    {
//                        log.Info("接收服务器向客户端下发用户权限下指定分组的车辆静态数据1011:" + message + "-----" + DateTime.Now.ToLocalTime());
//
//                    }
//                    else if (head.Contains("1007"))
//                    {
//                        log.Info("服务器向客户端转发车辆实时定位数据1007:" + message + "-----" + DateTime.Now.ToLocalTime());
//                        bool flag_start = true;
//                        Track tt = JsonConvert.DeserializeObject<Track>(body);
//                        string gpstime = tt.gpstime;//获取的轨迹时间
//                        DateTime gpsdatetime = DateTime.Parse(DateTime.Parse(gpstime).ToString("yyyy-MM-dd HH:mm:ss"));
//                        DateTime nowDate = DateTime.Now.ToLocalTime() ;//当前时间
//                        TimeSpan timeSpan = nowDate - gpsdatetime;
//                        if (timeSpan.TotalSeconds > 300|| timeSpan.TotalSeconds<-300)//时间超过300秒
//                            flag_start = false;
//                        if(flag_start&& tt.av=="1")//合理的数据则插入 ,有效的定位
//                            dataDB.insertTrack(tt);
//                    }
//                    else
//                    {
//
//                    }
//                }
//            }
//            //显示消息
//            //WriteLine(message, ConsoleColor.White);
//
//            //接收下一个消息(因为这是一个递归的调用，所以这样就可以一直接收消息了）
//            clientSocket.BeginReceive(data, 0, data.Length, SocketFlags.None, new AsyncCallback(ReceiveMessage), clientSocket);
//        }
//        catch (Exception ex)
//        {
//            log.Error("接收数据异常：" + ex.Message);
//        }
//    }
//    /// <summary>
//    /// 心跳包
//    /// </summary>
//    /// <param name="source"></param>
//    /// <param name="e"></param>
//    //
//    public void HeartBucket(object source, System.Timers.ElapsedEventArgs e) {
//        //判断当前时间和上一次接收心跳包的时间差
//        DateTime nowDate = DateTime.Now.ToLocalTime();
//        if (heartPackTime != null)
//        {
//            TimeSpan timeSpan = nowDate - heartPackTime;
//            if (timeSpan.TotalMinutes > 2)//2分钟没有心跳包返回，说明连接断开
//            {
//                string msg = "";
//                if (!clientSocket.Connected)
//                {
//                    log.Info("2分钟没有接收到心跳包的返回，重连");
//                    CreateConnect(serverInfo, out msg);
//                    if (clientSocket.Connected)
//                        Send_login(userInfo);
//                }
//            }
//
//        }
//
//
//
//        string cmd = Cmd.Cmd_Heart();
//        byte[] sendData = System.Text.Encoding.Default.GetBytes(cmd);
//
//        clientSocket.BeginSend(sendData, 0, sendData.Length, SocketFlags.None, null, null);
//    }
//    /// <summary>
//    /// 1010 指令
//    /// </summary>
//    /// <param name="source"></param>
//    /// <param name="e"></param>
//    /// <param name="groupId"></param>
//    public void SendCmd_1010(object source, System.Timers.ElapsedEventArgs e,string groupId)
//
//    {
//        string cmd_1010 = Cmd.Cmd_1010(groupId);
//        byte[] sendData = System.Text.Encoding.Default.GetBytes(cmd_1010);
//
//        clientSocket.BeginSend(sendData, 0, sendData.Length, SocketFlags.None, null, null);
//    }
//        #endregion
//    /// <summary>
//    /// 登录指令
//    /// </summary>
//    /// <param name="userInfo"></param>
//    public void Send_login(UserInfo userInfo)
//    {
//        string cmd_1000 = Cmd.Cmd_Login(userInfo);
//        byte[] sendData = System.Text.Encoding.Default.GetBytes(cmd_1000);
//
//        clientSocket.BeginSend(sendData, 0, sendData.Length, SocketFlags.None, null, null);
//    }
//
//
//}
//}
//
