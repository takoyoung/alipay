package com.h5game.alipaylib;

import android.app.Activity;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.h5game.thirdpartycallback.ThirdPartyCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class Alipay extends ThirdPartyCallback {

    public Alipay(Activity activity){
        this.mActivity = activity;
    }

    public void onAlipayEvent(String eventName, int callbackId, Map args){
        if (eventName.equals("Login")){

        }else if(eventName.equals("Pay")){
            doPay(callbackId, args);
        }
    }

    private void doPay(int callbackId, final Map map){
        JSONObject payData = new JSONObject(map);
        Map<String, String> params = AlipayParams.buildOrderParamMap(payData);
        String orderParam = AlipayParams.buildOrderParam(params);   // 订单信息
        String signStr = "";
        try {
            signStr = URLEncoder.encode(payData.getString("sign"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String orderInfo = orderParam + "&sign=" + signStr;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map <String,String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = CALL_SUCCESS;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
