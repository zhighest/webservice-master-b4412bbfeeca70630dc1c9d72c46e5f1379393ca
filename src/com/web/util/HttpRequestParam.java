package com.web.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpRequestParam {

    private static final Log logger = LogFactory.getLog(HttpRequestParam.class);

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        InputStreamReader isr = null;
        try {
            String urlNameString = url;
            if (StringUtils.isNotBlank(param)) {
                urlNameString = urlNameString + "?" + param;
            }
            logger.info("url：" + urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("lbTokenValidate", "Zaq1@WSxCDe3$Rfv");
            //多版本加密
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            
            if (null != attributes) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                if (null != request) {
                    String sv = request.getHeader("sv");
                    if (StringUtils.isNotBlank(sv)) {
                        connection.setRequestProperty("sv", sv);
                    }
                    connection.setRequestProperty("clientIp", CommonUtil.getClientIP(request));
                    Object smDeviceId = request.getAttribute("smDeviceId");
                    if (smDeviceId!=null) {
                        connection.setRequestProperty("smDeviceId", (String)smDeviceId);
                    }
                }
            }
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info(key + "-" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            isr = new InputStreamReader(connection.getInputStream());
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) in.close();
                if (isr != null) isr.close();
            } catch (Exception e) {
                logger.error("关闭流异常", e);
            }
        }
        return result;
    }

    public static String sendGet4Map(String url, Map<String, Object> params) {
        String param = "";
        for (String key : params.keySet()) {
            param += key + "=" + String.valueOf(params.get(key)) + "&";
        }
        if (param.endsWith("&"))
            param = param.substring(0, param.length() - 1);
        return sendGet(url, param);
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL 所代表远程资源的响应
     * 使用jdk自带的HttpURLConnection向URL发送POST请求并输出响应结果
     * 参数使用流传递，并且硬编码为字符串"name=XXX"的格式
     * @throws Exception
     */
    public static String sendPost(String url, String params) {

        BufferedReader responseReader = null;
        InputStreamReader inputStream = null;
        StringBuffer buffer = new StringBuffer();
        try {
            logger.info("发送POST请求结果url：" + url);
            logger.info("发送POST请求结果params：" + params);
            URL urlPath = new URL(url);

            HttpURLConnection httpConn = (HttpURLConnection) urlPath.openConnection();
            httpConn.setConnectTimeout(120000);
            httpConn.setReadTimeout(120000);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpConn.setRequestProperty("lbTokenValidate", "Zaq1@WSxCDe3$Rfv");

            //多版本加密
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                if (null != request) {
                    String sv = request.getHeader("sv");
                    if (StringUtils.isNotBlank(sv)) {
                        httpConn.setRequestProperty("sv", sv);
                    }
                }
            }

            httpConn.connect();
            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            dos.writeBytes(params);
            dos.flush();
            dos.close();

            int resultCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                inputStream = new InputStreamReader(httpConn.getInputStream(), "UTF-8");
                responseReader = new BufferedReader(inputStream);
                String readLine;
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine);
                }
            }
        } catch (Exception e) {
            logger.error("发送POST请求出现异常", e);
        } finally {
            try {
                if (responseReader != null) responseReader.close();
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                logger.error("关闭流异常", e);
            }
        }
        logger.error("发送POST请求结果" + buffer.toString());
        return buffer.toString();
    }
    
}
