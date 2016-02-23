package com.warmtel.gemi.model;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ConfigResult {
    private int resultCode;
    private String resultInfo;
    private ConfigInfo info;
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public ConfigInfo getInfo() {
        return info;
    }

    public void setInfo(ConfigInfo info) {
        this.info = info;
    }


}
