package com.frank.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Slf4j
public class HttpUtil {

    //创建http client
    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "feca4dcb1241d2564ff37534a9f509705a5154664ff39364849639145ab9f1b3";

    public static CloseableHttpClient getClient(){
        return createHttpsClient();
    }
    public static void main(String[] args) throws Exception {
        //根据api store页面上实际的api url来发送get请求，获取数据
        String url= "https://api.wmcloud.com/data/v1/api/fundamental/getFdmtIS.json?ticker=000002&secID=&beginDate=&endDate=&publishDateBegin=&publishDateEnd=&reportType=&field=";
     //   String url = "https://api.wmcloud.com/data/v1/api/market/getMktEqud.csv?field=&beginDate=20150101&endDate=&secID=";
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        httpGet.addHeader("Connection", "keep-alive");

        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        //System.out.println(body);
        log.info("body:{}",body);
    }
    //创建http client
    public static CloseableHttpClient createHttpsClient() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
            sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}

