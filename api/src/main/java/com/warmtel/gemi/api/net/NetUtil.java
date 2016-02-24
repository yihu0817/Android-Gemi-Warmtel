package com.warmtel.gemi.api.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    public String mBaseUrl = "http://www.warmtel.com:8080/";
    private static NetUtil NETUTIL = null;

    public static NetUtil getInstance() {
        if (NETUTIL == null) {
            NETUTIL = new NetUtil();
        }
        return NETUTIL;
    }

    private NetUtil() {
    }

    public String getDataByConnectNet(String httpUrl) throws IOException {
        mBaseUrl = "http://www.warmtel.com:8080/" + httpUrl;
        Log.e("tag",mBaseUrl);
        URL url = new URL(mBaseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(8000);
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            return readStrFromInputStream(inputStream);
        } else {
            return null;
        }
    }

    /**
     * 从输入流读数据
     *
     * @param is
     * @return
     * @throws IOException
     */
    public String readStrFromInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Log.e("tag","sb.toString()  "+sb.toString());
        reader.close();
        is.close();
        return sb.toString();
    }
}
