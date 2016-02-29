package com.warmtel.gemi.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 全部直接存接口返回的JSON字符串
 *
 * @author vik.Zhou
 * @version 1.0
 */
public class ApiPreference {

    private static ApiPreference mInstance;
    private SharedPreferences mPreferences;
    public static final String NAME = "com.warmtel.gemi.PREFERENCE_API_CACHE";

    private ApiPreference(Context context) {
        mPreferences = context.getSharedPreferences(NAME, 0);
    }

    public synchronized static ApiPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiPreference(context);
        }
        return mInstance;
    }

    public void putCache(String key, String value) {
        mPreferences.edit().putString(key, value).commit();
    }

    public String getCache(String key) {
        return mPreferences.getString(key, null);
    }

    public void clearCache() {
        mPreferences.edit().clear().commit();
    }
}
