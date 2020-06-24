package com.rp.largegarbage.util;

import java.util.Random;

/**
 * @Description 随机生成一个i位数的验证码，验证码中可以有大写字母，小写字母，至少有一位数字
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/24 17:30
 */
public class RandomStringTLUtil {
    public static String randomNumeric(int i) {//返回值类型：String,参数：无
        Random r = new Random(); //Random生成随机数
        StringBuilder sb = new StringBuilder();//创建StringBuilder的对象
        while (true) {//无限循环
            int num = r.nextInt(122 - 48 + 1) + 48;//生成48--122的随机数num。
            if (num >= 48 && num <= 57) {//首先判断随机数num是否在0-9范围内的
                char bb = (char) num;//把随机数num强转位char类型，目的是为了添加到sb对象中。
                sb.append(bb);//满足条件添加到对象中。
            }
            if (num >= 65 && num <= 90 || num >= 97 && num <= 122) {//然后判断随机数num是否在a-z或者A-Z里
                char cc = (char) num;//强转
                sb.append(cc);//添加到对象
            }
            if (sb.length() == i) {//判断随机数长度是否为i
                break;//为i就退出循环
            }
        }
        String s = sb.toString();//由StringBuilder强转为String类型并接收
        return s;//返回值
    }

}
