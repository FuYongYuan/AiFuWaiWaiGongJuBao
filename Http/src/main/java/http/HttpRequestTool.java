package http;

import enumerate.CharsetFormat;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http请求封装
 */
public class HttpRequestTool {
    //--------------------------------------------------------------------------静态参数

    /**
     * 是否显示请求后的状态码
     **/
    public static int isOutputLog = 0;

    //--------------------------------------------------------------------------POST请求
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, null, params);
    }

    public static String doPost(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        return doPost(url, headers, params, CharsetFormat.UTF_8);
    }

    public static String doPost(String url, Map<String, String> headers, Map<String, String> params, CharsetFormat format) throws IOException {
        String content = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, format.getValue()));
        }
        if (headers != null && !headers.isEmpty()) {
            addHeader(httpPost, headers);
        }

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (isOutputLog == 1) {
                System.out.println(response.getStatusLine().getStatusCode() + ":" + HttpStateCode.state.get(response.getStatusLine().getStatusCode()));
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, format.getValue());
                //关闭entity
                EntityUtils.consume(entity);
            }
        }
        httpClient.close();
        return content;
    }

    public static String doPost(HttpPost httpPost) throws IOException {
        return doPost(httpPost, CharsetFormat.UTF_8);
    }

    public static String doPost(HttpPost httpPost, CharsetFormat format) throws IOException {
        String content = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (isOutputLog == 1) {
                System.out.println(response.getStatusLine().getStatusCode() + ":" + HttpStateCode.state.get(response.getStatusLine().getStatusCode()));
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, format.getValue());
                //关闭entity
                EntityUtils.consume(entity);
            }
        }
        httpClient.close();
        return content;
    }
    //--------------------------------------------------------------------------GET请求

    public static String doGet(String url) throws IOException {
        return doGet(url, null, null, CharsetFormat.UTF_8);
    }

    public static String doGet(String url, CharsetFormat format) throws IOException {
        return doGet(url, null, null, format);
    }

    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        return doGet(url, headers, params, CharsetFormat.UTF_8);
    }

    public static String doGet(String url, Map<String, String> headers, Map<String, String> params, CharsetFormat format) throws IOException {
        String content = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (params != null && !params.isEmpty()) {
            url = jointUrl(url, params);
        }
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            addHeader(httpGet, headers);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (isOutputLog == 1) {
                System.out.println(response.getStatusLine().getStatusCode() + ":" + HttpStateCode.state.get(response.getStatusLine().getStatusCode()));
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity, format.getValue());
                //关闭entity
                EntityUtils.consume(entity);
            }
        }
        httpClient.close();
        return content;
    }

    //-------------------------------------------------------------------------登录方法
    public static Map<String, String> doLoginPOST(String url, Map<String, String> headers, Map<String, String> params, CharsetFormat format) throws IOException {
        Map<String, String> cookieMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, format.getValue()));
        }
        if (headers != null && !headers.isEmpty()) {
            addHeader(httpPost, headers);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (isOutputLog == 1) {
                System.out.println(response.getStatusLine().getStatusCode() + ":" + HttpStateCode.state.get(response.getStatusLine().getStatusCode()));
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                for (int i = 0; i < response.getAllHeaders().length; i++) {
                    if (response.getAllHeaders()[i].getName().equalsIgnoreCase("set-cookie")) {
                        String cookie = response.getAllHeaders()[i].getValue();
                        int i1 = cookie.indexOf("=");
                        int i2 = cookie.indexOf(";");
                        if (i1 != -1 && i2 != -1) {
                            String _value = cookie.substring(i1 + 1, i2);
                            String _key = cookie.substring(0, i1);
                            cookieMap.put(_key, _value);
                        }
                    }
                }
            }
        }
        httpClient.close();
        return cookieMap;
    }

    public static Map<String, String> doLoginGET(String url, Map<String, String> headers) throws IOException {
        Map<String, String> cookieMap = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            addHeader(httpGet, headers);
        }
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (isOutputLog == 1) {
                System.out.println(response.getStatusLine().getStatusCode() + ":" + HttpStateCode.state.get(response.getStatusLine().getStatusCode()));
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                for (int i = 0; i < response.getAllHeaders().length; i++) {
                    if (response.getAllHeaders()[i].getName().equalsIgnoreCase("set-cookie")) {
                        String cookie = response.getAllHeaders()[i].getValue();
                        int i1 = cookie.indexOf("=");
                        int i2 = cookie.indexOf(";");
                        if (i1 != -1 && i2 != -1) {
                            String _value = cookie.substring(i1 + 1, i2);
                            String _key = cookie.substring(0, i1);
                            cookieMap.put(_key, _value);
                        }
                    }
                }
            }
        }
        httpClient.close();
        return cookieMap;
    }

    //--------------------------------------------------------------------------辅助方法
    private static void addHeader(HttpPost httpPost, Map<String, String> headers) {
        for (Map.Entry entry : headers.entrySet()) {
            httpPost.addHeader(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    private static void addHeader(HttpGet httpGet, Map<String, String> headers) {
        for (Map.Entry entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey().toString(), entry.getValue().toString());
        }
    }

    private static String jointUrl(String url, Map<String, String> params) {
        StringBuilder joint = new StringBuilder(url);
        if (url.contains("?")) {
            joint.append("&");
        } else {
            joint.append("?");
        }
        int i = 1;
        for (Map.Entry entry : params.entrySet()) {
            if (i == 1) {
                joint.append(entry.getKey().toString());
                joint.append("=");
                joint.append(entry.getValue().toString());
            } else {
                joint.append("&");
                joint.append(entry.getKey().toString());
                joint.append("=");
                joint.append(entry.getValue().toString());
            }
            i = i + 1;
        }
        return joint.toString();
    }

}
