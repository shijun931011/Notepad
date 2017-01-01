package com.example.shijun.notepad.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shijun.notepad.Bean.DepthPageTransformer;
import com.example.shijun.notepad.R;
import com.example.shijun.notepad.fragment.Guide1;
import com.example.shijun.notepad.fragment.Guide2;
import com.example.shijun.notepad.fragment.Guide3;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        Fragment guide1 = new Guide1();
        Fragment guide2 = new Guide2();
        Fragment guide3 = new Guide3();

        mFragment.add(guide1);
        mFragment.add(guide2);
        mFragment.add(guide3);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };

        // 为ViewPager添加动画效果,3.0以上可用
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
//        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mViewPager.setAdapter(mAdapter);


    }
}
