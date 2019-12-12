package com.example.moudle_wechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moudle_wechat.fragment.TabFragment;
import com.example.moudle_wechat.utils.L;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("1","2","3","4"));
    private Button mBtnHome;
    private Button mBtnAnli;
    private Button mBtnStar;
    private Button mBtnMe;
    private List<Button> mTabs = new ArrayList<>();
    private SparseArray<TabFragment> mFragments = new SparseArray<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                if (position == 0){
                    fragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWeChatTab(title);
                        }
                    });
                }
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
                    Button left = mTabs.get(position);
                    Button right = mTabs.get(position + 1);

                    left.setText((1-positionOffset)+"");
                    right.setText(positionOffset+"");
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
        mBtnHome = findViewById(R.id.btn_home);
        mBtnAnli = findViewById(R.id.btn_anli);
        mBtnStar = findViewById(R.id.btn_star);
        mBtnMe = findViewById(R.id.btn_me);

        mTabs.add(mBtnHome);
        mTabs.add(mBtnAnli);
        mTabs.add(mBtnStar);
        mTabs.add(mBtnMe);


        mBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取第一个Fragment
                TabFragment tabFragment = mFragments.get(0);
                if (tabFragment != null){
                    tabFragment.changeTitle("Welcome Home");
                }
            }
        });
    }

    public void changeWeChatTab(String title){
        mBtnHome.setText(title);
    }
}
