package com.rp.largegarbage.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description  服务器向客户端转发车辆实时定位数据
 *  code String 车牌号码
 * id String 车辆 ID
 * lat String 高德纬度
 * lat_bd String 百度纬度
 * lato String 原始纬度
 * lng String 高德经度
 * lng_bd String 百度经度
 * lngo String 原始经度
 * veo String 速度
 * dir String 方向
 * gpstime String 格式为：2011-11-01 12:38:39
 * av String 表示是否为有效定位， 0 无效定位， 1 有效定位
 * alarm String 是否报警， 0 无报警， 1 报警
 * oil String 当前油量
 * dis String 当前总里程
 * wstate String 整数类型状态
 * cstate String 状态描述，可以为空
 * posinfo String 位置描述，可以为空
 * p1 String 模拟量 1
 * p2 String 模拟量 2
 * temp float 温度
 * temp0 float 温度 1
 * temp1 float 温度 2
 * temp2 float 温度 3
 * temp3 float 温度 4
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 10:32
 */
@Data
public class CarInfoDTO implements Serializable {
    /*String carJson = "{\n" +
            "    \"header\": {\"cmd\": \"1007\"},\n" +
            "    \"body\": {\n" +
            "        \"code\": \"\",\n" +
            "        \"id\": \"\",\n" +
            "        \"lat\": \"\",\n" +
            "        \"lat_bd\": \"\",\n" +
            "        \"lato\": \"\",\n" +
            "        \"lng\": \"\",\n" +
            "        \"lng_bd\": \"\",\n" +
            "        \"lngo\": \"\",\n" +
            "        \"veo\": \"\",\n" +
            "        \"dir\": \"\",\n" +
            "        \"gpstime\": \"\",\n" +
            "        \"av\": \"\",\n" +
            "        \"alarm\": \"\",\n" +
            "        \"oil\": \"\",\n" +
            "        \"dis\": \"\",\n" +
            "        \"wstate\": \"\",\n" +
            "        \"cstate\": \"\",\n" +
            "        \"posinfo\": \"\",\n" +
            "        \"p1\": \"\",\n" +
            "        \"p2\": \"\",\n" +
            "        \"temp\": \"\",\n" +
            "        \"temp0\": \"\",\n" +
            "        \"temp1\": \"\",\n" +
            "        \"temp2\": \"\",\n" +
            "        \"temp3\": \"\"\n" +
            "    }\n" +
            "}";*/
    private String code;
    private String id;
    private String lat;
    private String lat_bd;
    private String lato;
    private String lng;
    private String lng_bd;
    private String lngo;
    private String veo;
    private String dir;
    private String gpstime;
    private String av;
    private String alarm;
    private String oil;
    private String dis;
    private String wstate;
    private String cstate;
    private String posinfo;
    private String p1;
    private String p2;
    private float temp;
    private float temp0;
    private float temp1;
    private float temp2;
    private float temp3;


}
