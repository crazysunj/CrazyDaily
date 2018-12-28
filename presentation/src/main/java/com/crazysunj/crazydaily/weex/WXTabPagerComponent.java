/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.crazydaily.weex;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.crazysunj.crazydaily.R;
import com.crazysunj.data.util.LoggerUtil;
import com.google.android.material.tabs.TabLayout;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author: sunjian
 * created on: 2018/3/14 下午1:51
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WXTabPagerComponent extends WXVContainer<LinearLayout> {


    private ViewPager mViewPager;
    private TabPagerAdapter mTabPagerAdapter;

    public WXTabPagerComponent(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected LinearLayout initComponentHostView(@NonNull Context context) {
        LinearLayout root = (LinearLayout) View.inflate(context, R.layout.layout_tab_pager, null);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.wx_tab);
        mViewPager = (ViewPager) root.findViewById(R.id.wx_pager);
        tabLayout.setupWithViewPager(mViewPager);
        return root;
    }

    @WXComponentProp(name = "titledata")
    public void setData(List<String> datas) {

        if (mTabPagerAdapter == null) {
            final Context context = getContext();
            if (!(context instanceof FragmentActivity)) {
                LoggerUtil.d("context不是FragmentActivity");
                return;
            }
            final FragmentActivity activity = (FragmentActivity) context;
            mTabPagerAdapter = new TabPagerAdapter(activity.getSupportFragmentManager(), datas);
            mViewPager.setAdapter(mTabPagerAdapter);
            return;
        }
        mTabPagerAdapter.reload(datas);
    }

    class TabPagerAdapter extends FragmentPagerAdapter {

        List<TabPagerFragment> mFragments;
        List<String> mTitleDatas;

        TabPagerAdapter(FragmentManager fm, List<String> datas) {
            super(fm);
            mTitleDatas = datas;
            mFragments = new ArrayList<>();
            for (String data : datas) {
                mFragments.add(TabPagerFragment.get(data));
            }
        }

        void reload(List<String> datas) {
            mTitleDatas.clear();
            mTitleDatas.addAll(datas);
            mFragments.clear();
            for (String data : datas) {
                mFragments.add(TabPagerFragment.get(data));
            }
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleDatas.get(position);
        }
    }
}
