package com.atguigu.commonutils;

import net.sf.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class HttpClientUtils {

    private static final int SIZE = 1024;

    /**
     * 指定安全套接字协议
     */
    private static final String PROTOCOL = "TLS";

    private static final String CHARSET_UTF8 = "UTF-8";
    /**
     * 提供用于安全套接字包
     */
    private static SSLContext context;
    /**
     * 此类是用于主机名验证的基接口 验证主机名和服务器验证方案的匹配是可接受的。 hostname - 主机名 session -
     * 到主机的连接上使用的SSLSession
     */
    private static final HostnameVerifier HOST_NAME_VERIFIER;

    /**
     * http client 连接池配置
     */
    private static final PoolingHttpClientConnectionManager CONN_MANAGER;
    /**
     * http 最大连接数
     */
    private static final Integer MAX_TOTAL = 500;
    /**
     * http 路由的默认最大连接 比如连接http://sishuok.com 和 http://qq.com时，到每个主机的并发最多只有
     * 200；即加起来是400（但不能超过400）
     */
    private static final Integer DEFAULT_MAX_PER_ROUTE = 50;

    static {
        CONN_MANAGER = new PoolingHttpClientConnectionManager();
        CONN_MANAGER.setMaxTotal(MAX_TOTAL);
        CONN_MANAGER.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        /**
         * 取消检测SSL 验证效验
         */
        X509TrustManager manager = new X509TrustManager() {
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
        try {
            context = SSLContext.getInstance(PROTOCOL);
            context.init(null, new TrustManager[]{manager}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        HOST_NAME_VERIFIER = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    /**
     * 获取 httpClient 支持 https请求访问
     *
     * @return
     * @author Johnson.Jia
     */
    public static CloseableHttpClient getHttpClient() {
        try {
            HttpResponseInterceptor itcp = new HttpResponseInterceptor() {
                @Override public void process(HttpResponse response, HttpContext context) {
                    response.setHeader(HttpHeaders.CONNECTION, "close");
                }
            };
            return HttpClients.custom().setSSLHostnameVerifier(HOST_NAME_VERIFIER).setSSLContext(context)
                    .setConnectionManager(CONN_MANAGER).addInterceptorFirst(itcp).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接 池 请勿轻易使用
     *
     * @author Johnson.Jia
     */
    public static void closeHttpClient(CloseableHttpClient httpClient) {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post 请求 默认字符集 UTF-8 数据格式 new UrlEncodedFormEntity(new
     * LinkedList<NameValuePair>())
     *
     * @param url   请求 url
     * @param param 请求参数 可为 null
     * @return
     * @author Johnson.Jia
     */
    public static Object httpPost(String url, Map<String, String> param) throws Exception {
        return httpPost(url, param, null);
    }

    /**
     * post 请求 数据格式 new UrlEncodedFormEntity(new LinkedList<NameValuePair>())
     *
     * @param url     请求 url
     * @param param   请求参数 可为 null
     * @param charset 默认 UTF-8
     * @return
     * @author Johnson.Jia
     */
    public static Object httpPost(String url, Map<String, String> param, String charset) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = null;
        if (StringUtils.isEmpty(charset))
            charset = CHARSET_UTF8;
        try {
            httpPost = new HttpPost(url);
            if (param != null) {
                List<NameValuePair> list = new LinkedList<NameValuePair>();
                Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, StringUtils.isEmpty(charset) ? "UTF-8" : charset);
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);
            return getResultObject(httpResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
    }

    /**
     * post 请求 默认字符集 UTF-8 数据格式 new StringEntity(json.toString()) 支持类型
     * application/json/html text/xml
     *
     * @param url   请求 url
     * @param param 请求参数 可为 null
     * @return
     * @author Johnson.Jia
     */
    public static Object httpPostJson(String url, JSONObject param) throws Exception {
        return httpPostJson(url, param, null);
    }

    /**
     * post 请求 数据格式 new StringEntity(json.toString()) 支持类型 application/json/html
     * text/xml
     *
     * @param url     请求 url
     * @param param   请求参数 可为 null
     * @param charset 默认 UTF-8
     * @return
     * @author Johnson.Jia
     */
    public static Object httpPostJson(String url, JSONObject param, String charset) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = null;
        if (StringUtils.isEmpty(charset))
            charset = CHARSET_UTF8;
        try {
            httpPost = new HttpPost(url);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + charset);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/html;charset=" + charset);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "text/xml;charset=" + charset);
            if (param == null) {
                param = new JSONObject();
            }
            httpPost.setEntity(new StringEntity(param.toString(), charset));
            httpResponse = httpClient.execute(httpPost);
            return getResultObject(httpResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
    }

    public static Object httpPostJson(String url, String param) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = null;
        String  charset = CHARSET_UTF8;
        try {
            httpPost = new HttpPost(url);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + charset);
            httpPost.setEntity(new StringEntity(param, charset));
            httpResponse = httpClient.execute(httpPost);
            return getResultObject(httpResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
    }

    public static Object httpPostJson(String url, JSONObject param, String charset,Map<String,String> headers) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = null;
        if (StringUtils.isEmpty(charset))
            charset = CHARSET_UTF8;
        try {
            httpPost = new HttpPost(url);
            for (Map.Entry<String,String> entry : headers.entrySet()){
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=" + charset);
            if (param == null) {
                param = new JSONObject();
            }
            httpPost.setEntity(new StringEntity(param.toString(), charset));
            httpResponse = httpClient.execute(httpPost);
            return getResultObject(httpResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
    }

    /**
     * Http get 请求
     *
     * @param url 默认字符集 utf8
     * @return
     * @author Johnson.Jia
     */
    public static Object httpGet(String url) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);
            httpResponse = httpClient.execute(httpGet);
            return getResultObject(httpResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
    }


    private static Object getResultObject(CloseableHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(httpResponse.getStatusLine().getStatusCode(), "http error!");
        }
        ContentType contentType = ContentType.get(httpResponse.getEntity());
        Charset charset = Charset.forName("UTF-8");
        String mimeType = null;
        if (contentType != null) {
            charset = contentType.getCharset();
            mimeType = contentType.getMimeType();
        }

        //暂时这样处理，后期优化
        if (charset == null) {
            charset = Charset.forName("UTF-8");
        }

        String object = EntityUtils.toString(httpResponse.getEntity(), charset);
        if (mimeType == null) {
            return object;
        }
        if (mimeType.contains("json")) {
            return JSONObject.fromObject(object);
        }
        if (mimeType.contains("xml") || mimeType.contains("html")) {
            return Jsoup.parse(object);
        }
        return object;
    }

}
