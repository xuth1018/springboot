package com.company.project.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String doGet(String uri){
        //建立客户端
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(uri);
        String result = null;
        HttpResponse response = null;
        try{
            //开启代理
/*          HttpHost proxy = new HttpHost("",20);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                    .setSocketTimeout(5000).setProxy(proxy).build();
            client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            */
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            response = client.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }else{
                logger.info("===HttpUtil.doGet()===请求失败");
                return null;
            }
        }catch(Exception e){
            logger.info("===HttpUtil.doGet()===error:{}",e);
        }finally {
            if (response != null) {
                try {
                    ((CloseableHttpResponse) response).close();;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doPost(String uri,String body){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(uri);
        String result = null;
        HttpResponse response = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            httpPost.addHeader("Content-Type","application/json");
            httpPost.addHeader("Accept","application/json");
            if(null!=body){
                httpPost.setEntity(new StringEntity(body));
            }
            response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity());
            }else{
                logger.info("===HttpUtil.doGet()===请求失败");
                return null;
            }
        }catch(Exception e){
            logger.info("===HttpUtil.doGet()===error:{}",e);
        }finally {
            if (response != null) {
                try {
                    ((CloseableHttpResponse) response).close();;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String uri = "http://192.186.43.71:8080/student/queryAll";
        ObjectMapper objectMapper = new ObjectMapper();
        String result = HttpUtil.doPost(uri,null);
        System.out.println(result);
    }
}
