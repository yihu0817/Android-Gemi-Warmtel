package com.warmtel.gemi.api;

import android.content.Context;

import com.google.gson.Gson;
import com.warmtel.gemi.api.net.NetUtil;
import com.warmtel.gemi.model.ConfigResult;
import com.warmtel.gemi.model.MerchantBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ApiImp implements Api {
    public Context mContext;

    public ApiImp(Context context) {
        this.mContext = context;
    }

    @Override
    public ConfigResult getNearbyConfigs() throws IOException {
        String configsStr = NetUtil.getInstance(mContext).getDataByConnectNet(configs);
        if (configsStr == null) {
            return null;
        }
        return new Gson().fromJson(configsStr, ConfigResult.class);
    }

    @Override
    public ArrayList<MerchantBean> getNearbyAround() throws IOException {
        String aroundStr = NetUtil.getInstance(mContext).getDataByConnectNet(around);
        if (aroundStr == null) {
            return null;
        }
        return parseJsonToMerchantList(aroundStr);
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
