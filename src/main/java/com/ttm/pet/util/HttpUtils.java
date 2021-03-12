package com.ttm.pet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * 发送https请求
     *
     * @param path
     * @param method
     * @param body
     * @return
     */
    public static String httpsRequestToString(String path, String method, String body) {
        if (path == null || method == null) {
            return null;
        }
        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {
            // 创建SSLConrext对象，并使用我们指定的信任管理器初始化
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            TrustManager[] tm = { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            } };
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上面对象中得到SSLSocketFactory
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（get|post）
            conn.setRequestMethod(method);

            // 有数据提交时
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();
        } catch (Exception e) {
            logger.error("发送https请求失败");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException execption) {
                logger.error("发送https请求失败 IOException ");
            }
        }
        return response;
    }
}
