package com.warmtel.gemi.ui.cheap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.warmtel.gemi.R;
import com.warmtel.gemi.ui.common.BannerLayout;

import java.util.ArrayList;
import java.util.List;

public class CheapFragment extends Fragment implements View.OnClickListener{
    private EditText mSearchKey;
    public static CheapFragment newInstance() {
        CheapFragment fragment = new CheapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheap_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchKey = (EditText) getView().findViewById(R.id.com_search_edit);
        mSearchKey.setOnClickListener(this);

        BannerLayout bannerLayout = (BannerLayout) getView().findViewById(R.id.banner);
        final List<String> urls = new ArrayList<>();
        urls.add("http://img1.naryou.cn/images/default/ad1.jpg");
        urls.add("http://img1.naryou.cn/images/default/ad2.jpg");
        urls.add("http://img1.naryou.cn/images/default/ad3.jpg");
        bannerLayout.setViewUrls(urls);

        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.com_search_edit:
                getActivity().onSearchRequested();
                break;
        }
    }
}
