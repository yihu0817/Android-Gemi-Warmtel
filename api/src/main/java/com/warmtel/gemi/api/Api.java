package com.warmtel.gemi.api;

import com.warmtel.gemi.model.ConfigResult;
import com.warmtel.gemi.model.MerchantBean;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 定义接口
 */
public interface Api {
    public static final String configs = "configs";
    public static final String around = "around";

    public ConfigResult getNearbyConfigs() throws IOException;

    public ArrayList<MerchantBean> getNearbyAround() throws IOException;


}
