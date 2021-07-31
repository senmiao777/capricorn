package com.frank.spider.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2021/7/18 0018 上午 11:18
 */
public class AppleLoginPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        System.out.println("page=");
    }


    public static void main(String[] args) {
        Spider spider = Spider.create(new AppleLoginPageProcessor());
        Request account = new Request("https://idmsa.apple.com/appleauth/auth/federate?isRememberMeEnabled=true");
        Map<String, Object> nameValuePair = new HashMap<>();
        nameValuePair.put("accountName", "1042680038@qq.com");
        nameValuePair.put("rememberMe", false);
        account.setExtras(nameValuePair);


        System.out.println("1---------------------------");
        // Spider spider1 = Spider.create(new AppleLoginPageProcessor());
        Request password = new Request("https://idmsa.apple.com/appleauth/auth/signin?isRememberMeEnabled=true");
        Map<String, Object> pw = new HashMap<>();
        pw.put("accountName", "1042680038@qq.com");
        pw.put("rememberMe", false);
        pw.put("password", "No7wangjieru");
        password.setExtras(nameValuePair);
        spider.addRequest(password)
                .thread(1)
                .run();
        System.out.println("2---------------------------");

        // Spider spider2 = Spider.create(new AppleLoginPageProcessor());
        Request code = new Request("https://idmsa.apple.com/appleauth/auth");

        spider.addRequest(account, password, code)
                .thread(1)
                .run();
    }
}
