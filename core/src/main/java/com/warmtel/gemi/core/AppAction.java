
package com.warmtel.gemi.core;

import com.warmtel.gemi.model.AutoMessageDTO;
import com.warmtel.gemi.model.ConfigResult;
import com.warmtel.gemi.model.MerchantBean;

import java.util.ArrayList;

/**
 * 接收app层的各种Action
 *
 */
public interface AppAction {
    /**
     * 获取二级菜单数据
     *
     * @param listener 回调监听器
     */
    public void getNearbyConfig(final ActionCallbackListener<ConfigResult> listener);

    /**
     * 获取附近列表数据
     * @param listener
     */
    public void getNearbyAround(final ActionCallbackListener<ArrayList<MerchantBean>> listener);

    public AutoMessageDTO getCheapAutoComplete(String wordKey);
}
