package com.warmtel.gemi.ui.near;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;
import com.warmtel.gemi.R;
import com.warmtel.gemi.model.CirclesBean;
import com.warmtel.gemi.model.ConfigInfo;
import com.warmtel.gemi.model.ConfigResult;
import com.warmtel.gemi.model.MerchantBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private ListView mListView;
    private MerchantAdapter mMerchantAdapter;

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
        mListView = (ListView) getView().findViewById(R.id.merchant_listview);

        mMerchantAdapter = new MerchantAdapter(getActivity());
        mListView.setAdapter(mMerchantAdapter);

        setExpandPopTabViewData();
        setListViewData();
    }

    /**
     * 设置二级菜单数据源
     */
    public void setExpandPopTabViewData(){
        String httpUrl = "http://www.warmtel.com:8080/configs";
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                try {
                    return getDataByConnectNet(params[0]);
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("tag","s :"+s);
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
                addItem(mExpandPopTabView, mParentLists, mChildrenListLists, "锦江区", "合江亭", "区域");

            }
        }.execute(httpUrl);
    }

    /**
     * 设置列表数据
     */
    public void setListViewData(){
        String httpUrl = "http://www.warmtel.com:8080/around";
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                try {
                    return getDataByConnectNet(params[0]);
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                ArrayList<MerchantBean> merchantLists = parseJsonToMerchantList(s);
                mMerchantAdapter.setListData(merchantLists);

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
                Toast.makeText(getActivity(), showText, Toast.LENGTH_SHORT).show();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }

    public String getDataByConnectNet(String httpUrl) throws IOException {
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
     * 解析Json字符串, 构造ListView数据源
     *
     * @return
     */
    public ArrayList<MerchantBean> parseJsonToMerchantList(String message) {
        ArrayList<MerchantBean> merchantList = new ArrayList<MerchantBean>();
        try {
            JSONObject jsonRoot = new JSONObject(message);
            JSONObject jsonInfo = jsonRoot.getJSONObject("info");
            JSONArray jsonMerchatArray = jsonInfo.getJSONArray("merchantKey");
            int length = jsonMerchatArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject jsonItem = jsonMerchatArray.getJSONObject(i);
                String name = jsonItem.getString("name");
                String coupon = jsonItem.getString("coupon");
                String location = jsonItem.getString("location");
                String distance = jsonItem.getString("distance");
                String picUrl = jsonItem.getString("picUrl");
                String couponType = jsonItem.getString("couponType"); // 券
                String cardType = jsonItem.getString("cardType"); // 卡
                String groupType = jsonItem.getString("groupType"); // 团

                MerchantBean merchant = new MerchantBean();
                merchant.setName(name);
                merchant.setCoupon(coupon);
                merchant.setLocation(location);
                merchant.setDistance(distance);
                merchant.setPicUrl(picUrl);
                merchant.setCardType(cardType);
                merchant.setCouponType(couponType);
                merchant.setGroupType(groupType);

                merchantList.add(merchant);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return merchantList;
    }

    public class MerchantAdapter extends BaseAdapter {
        private ArrayList<MerchantBean> merchantList = new ArrayList<MerchantBean>();
        private LayoutInflater layoutInflater;
        private Context context;

        public MerchantAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setListData(ArrayList<MerchantBean> list) {
            this.merchantList = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return merchantList.size();
        }

        @Override
        public Object getItem(int position) {
            return merchantList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            final ViewHodler holder;
            if (convertView == null) {
                v = layoutInflater.inflate(R.layout.item_merchat_layout, null);

                holder = new ViewHodler();
                holder.iconImg = (ImageView) v
                        .findViewById(R.id.merchant_icon_img);
                holder.nameTxt = (TextView) v
                        .findViewById(R.id.merchant_name_txt);
                holder.couponTxt = (TextView) v
                        .findViewById(R.id.merchant_coupon_txt);
                holder.loactionTxt = (TextView) v
                        .findViewById(R.id.merchant_loaction_txt);
                holder.distanceTxt = (TextView) v
                        .findViewById(R.id.merchant_distance_txt);
                holder.cardImg = (ImageView) v
                        .findViewById(R.id.merchant_card_img);
                holder.groupImg = (ImageView) v
                        .findViewById(R.id.merchant_group_img);
                holder.conponImg = (ImageView) v
                        .findViewById(R.id.merchant_counp_img);

                v.setTag(holder);
            } else {
                v = convertView;
                holder = (ViewHodler) v.getTag();
            }

            MerchantBean merchant = (MerchantBean) getItem(position);

//            AsyncMemoryFileCacheImageLoader.getInstance(context).loadBitmap(
//                    getResources(), merchant.getPicUrl(), holder.iconImg);
            Picasso.with(context).load(merchant.getPicUrl()).into(holder.iconImg);

            holder.nameTxt.setText(merchant.getName());
            holder.couponTxt.setText(merchant.getCoupon());
            holder.loactionTxt.setText(merchant.getLocation());
            holder.distanceTxt.setText(merchant.getDistance());

            if (merchant.getCardType().equalsIgnoreCase("YES")) {
                holder.cardImg.setVisibility(View.VISIBLE);
            } else {
                holder.cardImg.setVisibility(View.GONE);
            }

            if (merchant.getGroupType().equalsIgnoreCase("YES")) {
                holder.groupImg.setVisibility(View.VISIBLE);
            } else {
                holder.groupImg.setVisibility(View.GONE);
            }

            if (merchant.getCouponType().equalsIgnoreCase("YES")) {
                holder.conponImg.setVisibility(View.VISIBLE);
            } else {
                holder.conponImg.setVisibility(View.GONE);
            }
            return v;
        }

        public class ViewHodler {
            ImageView iconImg; // 图标
            TextView nameTxt; // 标题
            TextView couponTxt; // 打折信息
            TextView loactionTxt; // 地址
            TextView distanceTxt; // 距离
            ImageView cardImg; // 卡
            ImageView groupImg; // 团
            ImageView conponImg; // 券
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
