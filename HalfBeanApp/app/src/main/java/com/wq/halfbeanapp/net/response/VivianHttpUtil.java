package com.wq.halfbeanapp.net.response;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vivianWQ on 2017/11/22
 * Mail: wangqi_vivian@sina.com
 * desc: 网络请求基本工具类
 * Version: 1.0
 */
public class VivianHttpUtil {

    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final int connectTimeOut = 6000;

    public static final int readTimeOut = 30000;


    /**
     * POST方式发送数据
     *
     * @param http
     * @param data
     * @param charset
     * @return
     */
    public static String sendPost(String http, Object data, String charset) {
        return request(http, data, charset, POST, true);
    }

    public static String sendPost1(String http, Object data, String charset) {
        return request(http, data, charset, POST, false);
    }


    private static String request(String http, Object data, String charset, String type, boolean isObject) {

        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;


        try {
            if (GET.equals(type) && data != null && !"".equals(data)) {
                http = http + "?" + data;
            }
            URL url = new URL(http);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(type);
            connection.setConnectTimeout(connectTimeOut);
            connection.setReadTimeout(readTimeOut);
            connection.setRequestProperty("accept", "application/json");

            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("uid","1");
            connection.setUseCaches(false);
            if (POST.equals(type))
                connection.setDoOutput(true);
            connection.setDoInput(true);
            String strParam;
            if (isObject) {
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                strParam = JsonTools.getJsonString(data);
            } else {
                strParam = data.toString();
            }
            //传送数据
            //传送数据
            if (POST.equals(type)) {
                if (data != null && !"".equals(data)) {
                    byte[] writeBytes = strParam.getBytes();
                    if (isObject) {
                        connection.setRequestProperty("Content-Length", String.valueOf(writeBytes.length));
                    }
                    OutputStream outWriteStream = connection.getOutputStream();
                    outWriteStream.write(strParam.getBytes());
                    outWriteStream.flush();
                    outWriteStream.close();
                }
            }
            //接收数据
            if (connection.getResponseCode() == 200) {
                inputStreamReader = new InputStreamReader(connection.getInputStream(), charset);

                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line + "\n");
                }

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (connection != null)
                    connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString().trim();
        }


    }
}
