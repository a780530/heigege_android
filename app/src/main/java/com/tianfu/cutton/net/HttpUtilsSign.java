package com.tianfu.cutton.net;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by admin on 2017/9/19.
 */

public class HttpUtilsSign {
    String webUrl = "http://www.baidu.com";//百度

    public String sign(Map<String, String> paramMap) {
        String secret = "QYH9moBZgZf*3XB!jCUAVxH@jt&Z4&qP";
        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        stringBuilder.append(secret);
       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        long lt = new Long(System.currentTimeMillis());
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);*/
        String websiteDatetime = getWebsiteDatetime(webUrl);
        stringBuilder.append(websiteDatetime);
        String codes = stringBuilder.toString();
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();
        return sign;
    }
    private static String getWebsiteDatetime(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
