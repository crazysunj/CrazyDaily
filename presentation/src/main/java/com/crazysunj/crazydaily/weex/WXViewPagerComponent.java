package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.crazysunj.crazydaily.R;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * author: sunjian
 * created on: 2018/3/14 下午1:51
 * description:
 */

public class WXViewPagerComponent extends WXVContainer<LinearLayout> {


    private ViewPager viewPager;

    public WXViewPagerComponent(WXSDKInstance instance, WXDomObject node, WXVContainer parent) {
        super(instance, node, parent);
    }

    @Override
    protected LinearLayout initComponentHostView(@NonNull Context context) {
        LinearLayout root = (LinearLayout) View.inflate(context, R.layout.layout_tab_pager, null);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.wx_tab);
        viewPager = (ViewPager) root.findViewById(R.id.wx_pager);
        tabLayout.setupWithViewPager(viewPager);
//        List<String> datas = new ArrayList<>();
//        datas.add(GankioEntity.ResultsEntity.PARAMS_ANDROID);
//        datas.add(GankioEntity.ResultsEntity.PARAMS_IOS);
//        datas.add(GankioEntity.ResultsEntity.PARAMS_H5);
//        List<TabPagerFragment> fragments = new ArrayList<>();
//        for (String data : datas) {
//            fragments.add(TabPagerFragment.get(data));
//        }
//        viewPager.setAdapter(new FragmentPagerAdapter(((FragmentActivity)context).getSupportFragmentManager()) {
//
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return datas.get(position);
//            }
//        });
        Log.d("WXViewPagerComponent", "initComponentHostView");
        return root;
    }

    @WXComponentProp(name = "titledata")
    public void setData(List<String> datas) {
        Log.d("WXViewPagerComponent", datas.toString());
        List<TabPagerFragment> fragments = new ArrayList<>();
        for (String data : datas) {
            fragments.add(TabPagerFragment.get(data));
        }
        Context context = getContext();
        if (!(context instanceof FragmentActivity)) {
            Log.d("WXViewPagerComponent", "context不是FragmentActivity");
            return;
        }
        FragmentActivity activity = (FragmentActivity) context;
        viewPager.setAdapter(new FragmentPagerAdapter(activity.getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return datas.get(position);
            }
        });
    }
}
