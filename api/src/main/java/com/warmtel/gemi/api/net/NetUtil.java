package com.warmtel.gemi.api.net;

import android.content.Context;
import android.util.Log;

import com.warmtel.gemi.api.Api;
import com.warmtel.gemi.api.utils.ApiPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    public String mBaseUrl = "http://www.warmtel.com:8080/";
    private static NetUtil NETUTIL = null;
    public Context mContext;
    public static NetUtil getInstance(Context context) {
        if (NETUTIL == null) {
            NETUTIL = new NetUtil(context);
        }
        return NETUTIL;
    }

    private NetUtil(Context context) {
        this.mContext = context;
    }

    public String getDataByConnectNet(String httpUrl) throws IOException {
        mBaseUrl = Api.BASE_URL + httpUrl;
        URL url = new URL(mBaseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(15000);
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            String responseStr =  readStrFromInputStream(inputStream);
            // TODO: 2016/2/29  添加缓存
            ApiPreference.getInstance(mContext).putCache(mBaseUrl,responseStr);

            Log.e("tag","添加缓存 >>>>>");
            return responseStr;
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
        Log.e("tag","json :"+sb.toString());
        reader.close();
        is.close();
        return sb.toString();
    }
}
