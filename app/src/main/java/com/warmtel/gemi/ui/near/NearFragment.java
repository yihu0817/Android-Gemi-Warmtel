package com.warmtel.gemi.ui.near;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;
import com.warmtel.gemi.R;
import com.warmtel.gemi.model.CirclesBean;
import com.warmtel.gemi.model.ConfigInfo;
import com.warmtel.gemi.model.ConfigResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NearFragment extends Fragment {
    private ExpandPopTabView mExpandPopTabView;

    public static NearFragment newInstance() {
        NearFragment fragment = new NearFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_near_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mExpandPopTabView = (ExpandPopTabView) getView().findViewById(R.id.near_expandpoptabview);

        String httpUrl = "http://www.warmtel.com:8080/configs";
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                try {
                    return getData(params[0]);
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("tag", "s :" + s);
                Gson  gson = new Gson();
                ConfigResult configResult =  gson.fromJson(s, ConfigResult.class);
                ConfigInfo info = configResult.getInfo();

                addItem(mExpandPopTabView, info.getDistanceKey(), "", "距离");
                addItem(mExpandPopTabView, info.getIndustryKey(), "", "行业");
                addItem(mExpandPopTabView, info.getSortKey(), "", "排序");

                List<KeyValueBean> mParentLists = new ArrayList<>();//父区域
                List<ArrayList<KeyValueBean>> mChildrenListLists = new ArrayList<>();//子区域

                for (CirclesBean circlesBean : info.getAreaKey()){
                    KeyValueBean keyValueBean = new KeyValueBean();
                    keyValueBean.setKey(circlesBean.getKey());
                    keyValueBean.setValue(circlesBean.getValue());
                    mParentLists.add(keyValueBean);
                    mChildrenListLists.add((ArrayList<KeyValueBean>) circlesBean.getCircles());
                }
                addItem(mExpandPopTabView,mParentLists,mChildrenListLists,"锦江区","合江亭","区域" );

            }
        }.execute(httpUrl);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(getActivity());
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                //弹出框选项点击选中回调方法
                Toast.makeText(getActivity(),value,Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }
    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists, String defaultParentSelect, String defaultChildSelect, String defaultShowText) {
        PopTwoListView popTwoListView = new PopTwoListView(getActivity());
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {
            @Override
            public void getValue(String showText, String parentKey, String childrenKey) {
                Toast.makeText(getActivity(),showText,Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }

    public String getData(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(8000);

        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            return readStrFromInputStream(inputStream);
        }else{
            return null;
        }
    }

    /**
     * 从输入流读数据
     *
     * @param is
     * @return
     * @throws IOException
     */
    public String readStrFromInputStream(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        is.close();
        return sb.toString();
    }
}
