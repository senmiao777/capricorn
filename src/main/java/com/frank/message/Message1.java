package com.frank.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Administrator
 */
public class Message1 {

    /**
     * 用户名
     */
    private static String Uid = "woxiangjingjing";

    /**
     * 接口安全秘钥
     */
    private static String Key = "d41d8cd98f00b204e980";

    /**
     * 短信号码，多个号码如13800000000,13800000001,13800000002
     */
    private static String smsMob = "13240411778";

    /**
     * 短信内容
     */
    private static String smsText = "【货物更新】订单号86-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));


    public static void main(String[] args) {

        HttpClientUtil client = HttpClientUtil.getInstance();

        //UTF发送
        int result = client.sendMsgUtf8(Uid, Key, smsText, smsMob);
        if(result>0){
            System.out.println("UTF8成功发送条数=="+result);
        }else{
            System.out.println(client.getErrorMsg(result));
        }
    }
}