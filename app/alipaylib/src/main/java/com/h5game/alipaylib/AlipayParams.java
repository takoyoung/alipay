package com.h5game.alipaylib;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlipayParams {
    /**
     * 构造支付订单参数列表
     */
    public static Map<String, String> buildOrderParamMap(JSONObject payData) {
        Map<String, String> keyValues = new HashMap<String, String>();
        keyValues.put("app_id", payData.getString("app_id"));
        keyValues.put("biz_content", payData.getString("biz_content"));
        keyValues.put("charset", payData.getString("charset"));
        keyValues.put("method", payData.getString("method"));
        keyValues.put("sign_type", payData.getString("sign_type"));
        keyValues.put("timestamp", payData.getString("timestamp"));
        keyValues.put("version", payData.getString("version"));
        keyValues.put("notify_url", payData.getString("notify_url"));

        return keyValues;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map
     * 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
}
