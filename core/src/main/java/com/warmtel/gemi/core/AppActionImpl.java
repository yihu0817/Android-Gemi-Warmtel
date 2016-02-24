
package com.warmtel.gemi.core;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.warmtel.gemi.api.Api;
import com.warmtel.gemi.api.ApiImp;
import com.warmtel.gemi.model.ConfigResult;
import com.warmtel.gemi.model.MerchantBean;

import java.io.IOException;
import java.util.ArrayList;

/**
 * AppAction接口的实现类
 *
 */
public class AppActionImpl implements AppAction {

    private Context context;
    private Api mApi;

    public AppActionImpl(Context context) {
        this.context = context;
        this.mApi = new ApiImp();
    }

    @Override
    public void getNearbyConfig(final ActionCallbackListener<ConfigResult> listener) {
        new AsyncTask<String, Void, ConfigResult>(){
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
                Log.e("tag","configResult :"+configResult);
               if(configResult != null){
                   listener.onSuccess(configResult);
               }else{
                   listener.onFailure(ErrorEvent.PARAM_ILLEGAL,ErrorEvent.PARAM_NULL);
               }
            }
        }.execute();
    }

    @Override
    public void getNearbyAround(final ActionCallbackListener<ArrayList<MerchantBean>> listener) {
        new AsyncTask<String, Void, ArrayList<MerchantBean>>(){

            @Override
            protected ArrayList<MerchantBean> doInBackground(String... params) {
                try {
                    return  mApi.getNearbyAround();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<MerchantBean> merchantLists) {
                if(merchantLists != null){
                    listener.onSuccess(merchantLists);
                }else{
                    listener.onFailure(ErrorEvent.PARAM_ILLEGAL,ErrorEvent.PARAM_NULL);
                }
            }
        }.execute();
    }

}
