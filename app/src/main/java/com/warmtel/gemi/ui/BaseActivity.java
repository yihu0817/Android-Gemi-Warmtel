package com.warmtel.gemi.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.warmtel.gemi.GemiAppliction;

public  abstract class BaseActivity extends AppCompatActivity {
    public GemiAppliction mGemiApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGemiApp = (GemiAppliction) getApplication();
    }

}
