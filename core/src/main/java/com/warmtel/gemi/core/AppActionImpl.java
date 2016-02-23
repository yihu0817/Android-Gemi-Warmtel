
package com.warmtel.gemi.core;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AppAction接口的实现类
 *

 */
public class AppActionImpl implements AppAction {

    private final static int LOGIN_OS = 1; // 表示Android
    private final static int PAGE_SIZE = 20; // 默认每页20条

    private Context context;
//    private Api api;

    public AppActionImpl(Context context) {
        this.context = context;
//        this.api = new ApiImpl();
    }

    @Override
    public void sendSmsCode(final String phoneNum, final ActionCallbackListener<Void> listener) {
        // 参数检查
        if (TextUtils.isEmpty(phoneNum)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return;
        }
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(phoneNum);
        if (!matcher.matches()) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "手机号不正确");
            }
            return;
        }

//        // 请求Api
//        new AsyncTask<Void, Void, ApiResponse<Void>>() {
//            @Override
//            protected ApiResponse<Void> doInBackground(Void... voids) {
//                return api.sendSmsCode4Register(phoneNum);
//            }
//
//            @Override
//            protected void onPostExecute(ApiResponse<Void> response) {
//                if (listener != null && response != null) {
//                    if (response.isSuccess()) {
//                        listener.onSuccess(null);
//                    } else {
//                        listener.onFailure(response.getEvent(), response.getMsg());
//                    }
//                }
//            }
//        }.execute();
    }

    @Override
    public void register(String phoneNum, String code, String password, ActionCallbackListener<Void> listener) {

    }

}
