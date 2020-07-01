package com.rp.largegarbage.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/** 根据经纬度查询省市区名称
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 11:46
 */
public class GetConfigureInfoUtil {

    public static void main(String[] args) { // lat 39.97646 //log 116.3039
        String add = getAdd("116.3039", "39.97646");
        JSONObject jsonObject = JSONObject.parseObject(add);
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("addrList"));
        JSONObject j_2 = JSONObject.parseObject(jsonArray.get(0).toString());
        String allAdd = j_2.getString("admName");
        String arr[] = allAdd.split(",");
        System.out.println(add);
        System.out.println("省：" + arr[0] + "\n市：" + arr[1] + "\n区：" + arr[2]);
    }



    /**
     *
     * @Description: 根据经纬度 查询地址
     * @param @param log 纬度
     * @param @param lat 经度
     * @return String    String类型的json串
     * @throws
     */
    public static String getAdd(String longitude, String latitude) { // lat 小 log 大
        // 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l=" + latitude + "," + longitude + "&type=010";
        //String urlS = "http://api.map.baidu.com/geocoder/v2/?ak=wWYw0yCb8ntXmSgTxTx40vKR&callback=renderReverse&location=' + latitude + ',' + longitude + '&output=json&pois=1\n" ;
        String res = "";
        BufferedReader in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}

