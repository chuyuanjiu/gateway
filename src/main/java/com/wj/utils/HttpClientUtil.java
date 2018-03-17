package com.wj.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;

/**
 * @author wangjun
 * @date 18-3-10 下午3:43
 * @description
 * @modified by
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager cm = null;

    private final static String defaultCharset = "UTF-8";

    static {
        try {
            LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(20);
        }
        catch (Exception e) {
            throw new RuntimeException("create httpPool error", e);
        }
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }

    public static String sendGet(String url) {
        return sendGet(url, defaultCharset);
    }

    public static String sendGet(String url, String charset) {
        if (charset == null&&charset.length() == 0) {
            charset = defaultCharset;
        }
        CloseableHttpClient httpClient = getHttpClient();
        // 配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(20000).setRedirectsEnabled(true).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String result=null;
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(httpGet);
            if (200 == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toString(response.getEntity(), charset);
                return result;
            }
            else {
                result = EntityUtils.toString(response.getEntity(), charset);
                throw new RuntimeException(result);
            }

        }
        catch (Exception e) {
            throw new RuntimeException("http get error, url=" + url, e);
        }
        finally {
            try {
                response.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String sendPost(String url, String jsonStr) {
        return sendPost(url, jsonStr, defaultCharset);
    }

    public static String sendPost(String url, String jsonStr, String charset) {
        if (charset == null&&charset.length() == 0) {
            charset = defaultCharset;
        }
        CloseableHttpClient httpClient = getHttpClient();
        // 配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(20000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        String result=null;
        CloseableHttpResponse response=null;
        try {
            StringEntity entity = new StringEntity(jsonStr, defaultCharset);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode()) {
                result = EntityUtils.toString(response.getEntity(), charset);
                return result;
            }
            else {
                result = EntityUtils.toString(response.getEntity(), charset);
                throw new RuntimeException(result);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("http post error, url=" + url, e);
        }
        finally {
            try {
                response.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void main(String args[]) {
        String result = sendGet("https://www.baidu.com");
        System.out.println(result);
    }
}
