package com.example.moudle_wechat;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.moudle_wechat.fragment.TabFragment;
import com.example.moudle_wechat.utils.L;
import com.example.moudle_wechat.view.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityWithTab extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("1","2","3","4"));
    private TabView mBtnHome;
    private TabView mBtnAnli;
    private TabView mBtnStar;
    private TabView mBtnMe;
    private List<TabView> mTabs = new ArrayList<>();
    private SparseArray<TabFragment> mFragments = new SparseArray<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        initViews();
        
        initViewPagerAdapter();
        

    }

    private void initViewPagerAdapter() {

        mViewPager = findViewById(R.id.Viewpage_main);
        mViewPager.setOffscreenPageLimit(mTitles.size());
        //实现页面切换
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                TabFragment fragment = TabFragment.newInstance(mTitles.get(position));
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            //实现界面有几个Fragment，mFragments内就有几个Fragment
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment) super.instantiateItem(container, position);
                mFragments.put(position,fragment);
                return fragment;
            }

            //实现上述Fragment一一对应
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                L.d("onPageScrolled = "+ position +",positionOffset ="+positionOffset);

                // 左->右 tab:0->1,left pos,right pos+1,positionOffset0-1
                //左边预期的 progress:1-0(1-positionOffset0);右边预期的 progress:0-1 positionOffset0;

                // 右->左 tab:1->0,left pos,right pos+1,positionOffset1-0
                //左边预期的 progress:0-1(1-positionOffset0);右边预期的 progress:1-0 positionOffset0;

                if (positionOffset > 0){
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);

                    left.setProgress((1-positionOffset));
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                L.d("onPageSelected = "+ position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //初始化按钮
    private void initViews() {
        mViewPager = findViewById(R.id.Viewpage_main);
        mBtnHome = findViewById(R.id.tab_home);
        mBtnAnli = findViewById(R.id.tab_anli);
        mBtnStar = findViewById(R.id.tab_star);
        mBtnMe = findViewById(R.id.tab_me);

        mBtnHome.setIconAndText(R.drawable.shoucang_1,R.drawable.shoucang_selected,"收藏");
        mBtnAnli.setIconAndText(R.drawable.shoucang_1,R.drawable.shoucang_selected,"首页");
        mBtnStar.setIconAndText(R.drawable.shoucang_1,R.drawable.shoucang_selected,"首页");
        mBtnMe.setIconAndText(R.drawable.shoucang_1,R.drawable.shoucang_selected,"首页");

        mTabs.add(mBtnHome);
        mTabs.add(mBtnAnli);
        mTabs.add(mBtnStar);
        mTabs.add(mBtnMe);

    }

}
