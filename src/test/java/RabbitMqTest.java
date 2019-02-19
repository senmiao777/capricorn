import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/21 0021 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = RabbitMqTest.class)
@Rollback(false)
@Slf4j
public class RabbitMqTest {


    //定义Nagios的健康状态值
    public static final Integer EXIT_OK=0;
    public static final Integer EXIT_WARNING=1;
    public static final Integer EXIT_CRITICAL=2;
    public static final Integer EXIT_UNKNOWN=3;
    private static final String USERNAME="root";
    private static final String PASSWORD="look";
    //%2F代表"/"(默认的vhost)
    /**
     * %2F代表"/"(默认的vhost)
     * 如果想写你自己的交换机，在%2F后边直接加就行
     * 例如你的vhost是/test,那么访问的地址就是 .../%2Ftest
     */
    private static final String URL="http://192.168.31.22:15672/api/aliveness-test/%2Ffrank";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testRESTAMQPPingCheck(){
        HttpResponse response = null;
        int statusCode = 0;
        // String url = String.format(URL_DEFAULT, URLEncoder.encode("/frank"));
        String url = URL;
        try {
            DefaultHttpClient Client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            String up = USERNAME+":"+PASSWORD;
            //设置凭证
            String credentials = new BASE64Encoder().encode(up.getBytes("UTF-8"));
            httpGet.setHeader("Authorization","Basic "+credentials);
            response = Client.execute(httpGet);
            //读取响应内容
            BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseString = new StringBuilder();
            String line = null;
            while ((line = breader.readLine()) !=null) {
                responseString.append(line);
            }
            breader.close();
            String repsonseStr =responseString.toString();
            statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
        } catch (Exception e) {
            System.out.println("Could not connect to "+url);
            System.exit(EXIT_CRITICAL);
            e.printStackTrace();
        }
        //响应码大于299要么代表错误，要么就是发送给客户端额外的指令
        if(statusCode>299){
            System.out.println("Critical:Broker not alive:"+statusCode);
            System.exit(EXIT_CRITICAL);
        }
        System.out.println("OK! Connect to "+url+" successful ");
        System.exit(EXIT_OK);
    }

    @Test
    public void testRESTClusterCheck(){
        HttpResponse response = null;
        int statusCode = 0;
        String url="http://192.168.31.22:15672/api/queues/%2Ffrank/testQueue";
        try {
            DefaultHttpClient Client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            String up = USERNAME+":"+PASSWORD;
            //设置凭证
            String credentials = new BASE64Encoder().encode(up.getBytes("UTF-8"));
            httpGet.setHeader("Authorization","Basic "+credentials);
            response = Client.execute(httpGet);
            log.info("response={}",response);
            /**
             *   Object messages_unacknowledged = JSON.parseObject(response.toString()).get("messages_unacknowledged");
             log.info("messages_unacknowledged={}",messages_unacknowledged);
             Object messages_ready = JSON.parseObject(response.toString()).get("messages_ready");
             log.info("messages_ready={}",messages_ready);
             */
            //读取响应内容
            BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseString = new StringBuilder();
            String line = null;
            while ((line = breader.readLine()) !=null) {
                responseString.append(line);
            }
            breader.close();
            String repsonseStr =responseString.toString();
            statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode="+statusCode+" repsonseStr =" + repsonseStr);
        } catch (Exception e) {
            System.out.println("Could not connect to "+url);
            System.exit(EXIT_CRITICAL);
            e.printStackTrace();
        }
        //响应码大于299要么代表错误，要么就是发送给客户端额外的指令
        if(statusCode>299){
            System.out.println("Critical:Broker not alive:"+statusCode);
            System.exit(EXIT_CRITICAL);
        }
        System.out.println("OK! Connect to "+url+" successful ");
        System.exit(EXIT_OK);
    }


    @Test
    public void testt2() {
        log.info("RabbitMqTest start");
        for (int i = 1; i < 10; i++) {
            rabbitTemplate.convertAndSend(String.format("第%d个json串", i));
        }

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("RabbitMqTest end");
//        log.info("RabbitMqTest start rabbitTemplate={}", rabbitTemplate);
//        Message msg = rabbitTemplate.receive("testQueue");
//        log.info("RabbitMqTest end msg={}", msg);
    }

    @Test
    public void testt3() {
        log.info("RabbitMqTest start rabbitTemplate={}", rabbitTemplate);
        Message msg = rabbitTemplate.receive();
        log.info("RabbitMqTest end msg={}", msg);
    }


}
