package com.web.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RequestUtils
 * Created by R.hao on 2017/7/25.
 */
public class RequestUtils {

    private static final Log logger = LogFactory.getLog(RequestUtils.class);

    /**
     * 读取request流
     */
    public static String getQueryString(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request
                    .getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            logger.info("异常", e);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                logger.info("异常", e);
            }
        }
        return sb.toString();
    }
}
