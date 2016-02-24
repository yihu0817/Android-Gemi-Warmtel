package com.warmtel.gemi.ui;

import android.app.Application;

import com.warmtel.gemi.core.AppAction;
import com.warmtel.gemi.core.AppActionImpl;


public class GemiAppliction extends Application {
    public static AppAction mAction;
    @Override
    public void onCreate() {
        super.onCreate();
        mAction = new AppActionImpl(this);
    }
}
