package com.warmtel.gemi.model;

import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ConfigInfo {
    private List<CirclesBean> areaKey;
    private List<KeyValueBean> distanceKey;
    private List<KeyValueBean> industryKey;
    private List<KeyValueBean> sortKey;

    public List<CirclesBean> getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(List<CirclesBean> areaKey) {
        this.areaKey = areaKey;
    }

    public List<KeyValueBean> getDistanceKey() {
        return distanceKey;
    }

    public void setDistanceKey(List<KeyValueBean> distanceKey) {
        this.distanceKey = distanceKey;
    }

    public List<KeyValueBean> getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(List<KeyValueBean> industryKey) {
        this.industryKey = industryKey;
    }

    public List<KeyValueBean> getSortKey() {
        return sortKey;
    }

    public void setSortKey(List<KeyValueBean> sortKey) {
        this.sortKey = sortKey;
    }

}
