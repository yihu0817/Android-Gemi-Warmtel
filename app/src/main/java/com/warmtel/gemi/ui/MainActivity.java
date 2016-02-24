package com.warmtel.gemi.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.warmtel.gemi.R;
import com.warmtel.gemi.ui.cheap.CheapFragment;
import com.warmtel.gemi.ui.favor.FavorFragment;
import com.warmtel.gemi.ui.more.MoreFragment;
import com.warmtel.gemi.ui.near.NearFragment;
import com.warmtel.gemi.ui.pocket.PocketFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final int TAB_NEAR = 0;  //附近
    private static final int TAB_CHEAP = 1; //找便宜
    private static final int TAB_FAVOR = 2; //特惠
    private static final int TAB_POCKET = 3;//口袋
    private static final int TAB_MORE = 4;  //更多

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mNearRadioBtn, mCheapRadioBtn, mFavorRadioBtn, mPocketRadioBtn, mMoreRadioBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_layout);

        mViewPager = (ViewPager) findViewById(R.id.fragment_viewpager);
        mRadioGroup = (RadioGroup) findViewById(R.id.tab_radio_group);
        mNearRadioBtn = (RadioButton) findViewById(R.id.tab_radio_near);
        mCheapRadioBtn = (RadioButton) findViewById(R.id.tab_radio_cheap);
        mFavorRadioBtn = (RadioButton) findViewById(R.id.tab_radio_favor);
        mPocketRadioBtn = (RadioButton) findViewById(R.id.tab_radio_pocket);
        mMoreRadioBtn = (RadioButton) findViewById(R.id.tab_radio_more);

        ArrayList<Fragment> data = new ArrayList<Fragment>();
        data.add(NearFragment.newInstance());
        data.add(CheapFragment.newInstance());
        data.add(FavorFragment.newInstance());
        data.add(PocketFragment.newInstance());
        data.add(MoreFragment.newInstance());

        MyFragmentPagerViewAdatper adapter = new MyFragmentPagerViewAdatper(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        adapter.setDataFragmentList(data);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_radio_near:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_radio_cheap:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_radio_favor:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_radio_pocket:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.tab_radio_more:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_NEAR:
                        mNearRadioBtn.setChecked(true);
                        break;
                    case TAB_CHEAP:
                        mCheapRadioBtn.setChecked(true);
                        break;
                    case TAB_FAVOR:
                        mFavorRadioBtn.setChecked(true);
                        break;
                    case TAB_POCKET:
                        mPocketRadioBtn.setChecked(true);
                        break;
                    case TAB_MORE:
                        mMoreRadioBtn.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class MyFragmentPagerViewAdatper extends FragmentPagerAdapter {
        ArrayList<Fragment> dataFragmentList = new ArrayList<Fragment>();

        public void setDataFragmentList(ArrayList<Fragment> data) {
            dataFragmentList = data;
            notifyDataSetChanged();
        }

        public MyFragmentPagerViewAdatper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return dataFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return dataFragmentList.size();
        }
    }
}
