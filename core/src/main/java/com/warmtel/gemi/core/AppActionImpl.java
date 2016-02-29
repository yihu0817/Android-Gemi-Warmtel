
package com.warmtel.gemi.core;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.warmtel.gemi.api.Api;
import com.warmtel.gemi.api.ApiImp;
import com.warmtel.gemi.api.utils.ApiPreference;
import com.warmtel.gemi.model.AutoMessageDTO;
import com.warmtel.gemi.model.ConfigResult;
import com.warmtel.gemi.model.MerchantBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * AppAction接口的实现类
 */
public class AppActionImpl implements AppAction {

    private Context context;
    private Api mApi;

    public AppActionImpl(Context context) {
        this.context = context;
        this.mApi = new ApiImp(context);
    }

    @Override
    public void getNearbyConfig(final ActionCallbackListener<ConfigResult> listener) {
        new AsyncTask<String, Void, ConfigResult>() {
            @Override
            protected void onPreExecute() {
               String configsStr = ApiPreference.getInstance(context).getCache(Api.BASE_URL + Api.configs);
                if (configsStr != null) {
                    ConfigResult configResult =  new Gson().fromJson(configsStr, ConfigResult.class);
                    listener.onStart(configResult);
                }
            }

            @Override
            protected ConfigResult doInBackground(String... params) {
                try {
                    return mApi.getNearbyConfigs();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ConfigResult configResult) {
                if (configResult != null) {
                    listener.onSuccess(configResult);
                } else {
                    listener.onFailure(ErrorEvent.PARAM_ILLEGAL, ErrorEvent.PARAM_NULL);
                }
            }
        }.execute();
    }

    @Override
    public void getNearbyAround(final ActionCallbackListener<ArrayList<MerchantBean>> listener) {
        new AsyncTask<String, Void, ArrayList<MerchantBean>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                String aroundStr = ApiPreference.getInstance(context).getCache(Api.BASE_URL + Api.around);
                if (aroundStr != null) {
                    ArrayList<MerchantBean> MerchantLists = parseJsonToMerchantList(aroundStr);
                    listener.onStart(MerchantLists);
                }
            }

            @Override
            protected ArrayList<MerchantBean> doInBackground(String... params) {
                try {
                    return mApi.getNearbyAround();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<MerchantBean> merchantLists) {
                if (merchantLists != null) {
                    listener.onSuccess(merchantLists);
                } else {
                    listener.onFailure(ErrorEvent.PARAM_ILLEGAL, ErrorEvent.PARAM_NULL);
                }
            }
        }.execute();
    }

    @Override
    public AutoMessageDTO getCheapAutoComplete(String wordKey) {
        try {
            InputStream inputStream = context.getAssets().open("autoComplete");
            String jsonstr = readStrFromInputStream(inputStream);
            Gson gson = new Gson();
            Log.e("tag","jsonstr  :"+jsonstr);
            AutoMessageDTO autoMessageDTO = gson.fromJson(jsonstr, AutoMessageDTO.class);
            return autoMessageDTO;
        } catch (IOException e) {
            return null;
        }
    }

    public String readStrFromInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Log.e("tag", "sb.toString()  " + sb.toString());
        reader.close();
        is.close();
        return sb.toString();
    }

    /**
     * 解析Json字符串, 构造ListView数据源
     *
     * @return
     */
    public ArrayList<MerchantBean> parseJsonToMerchantList(String message) {
        ArrayList<MerchantBean> merchantList = new ArrayList<MerchantBean>();
        try {
            JSONObject jsonRoot = new JSONObject(message);
            JSONObject jsonInfo = jsonRoot.getJSONObject("info");
            JSONArray jsonMerchatArray = jsonInfo.getJSONArray("merchantKey");
            int length = jsonMerchatArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject jsonItem = jsonMerchatArray.getJSONObject(i);
                String name = jsonItem.getString("name");
                String coupon = jsonItem.getString("coupon");
                String location = jsonItem.getString("location");
                String distance = jsonItem.getString("distance");
                String picUrl = jsonItem.getString("picUrl");
                String couponType = jsonItem.getString("couponType"); // 券
                String cardType = jsonItem.getString("cardType"); // 卡
                String groupType = jsonItem.getString("groupType"); // 团

                MerchantBean merchant = new MerchantBean();
                merchant.setName(name);
                merchant.setCoupon(coupon);
                merchant.setLocation(location);
                merchant.setDistance(distance);
                merchant.setPicUrl(picUrl);
                merchant.setCardType(cardType);
                merchant.setCouponType(couponType);
                merchant.setGroupType(groupType);

                merchantList.add(merchant);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return merchantList;
    }
}
