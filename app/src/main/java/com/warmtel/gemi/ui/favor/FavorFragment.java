package com.warmtel.gemi.ui.favor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warmtel.gemi.R;

public class FavorFragment extends Fragment {

    public static FavorFragment newInstance() {
        FavorFragment fragment = new FavorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favor_layout, container, false);
    }

}
