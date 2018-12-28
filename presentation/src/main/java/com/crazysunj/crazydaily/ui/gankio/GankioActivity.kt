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
package com.crazysunj.crazydaily.ui.gankio

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crazysunj.crazydaily.R
import com.crazysunj.crazydaily.base.BaseActivity
import com.crazysunj.crazydaily.presenter.GankioPresenter
import com.crazysunj.domain.entity.gankio.GankioEntity
import kotlinx.android.synthetic.main.activity_gank_io.*

/**
 * @author: sunjian
 * created on: 2018/12/28 下午2:31
 * description: https://github.com/crazysunj/CrazyDaily
 */
class GankioActivity : BaseActivity<GankioPresenter>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, GankioActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        gank_io_tab.setupWithViewPager(gank_io_pager)
        gank_io_pager.adapter = TabPagerAdapter(supportFragmentManager, listOf(
                GankioTabFragment.get(GankioEntity.ResultsEntity.PARAMS_ANDROID),
                GankioTabFragment.get(GankioEntity.ResultsEntity.PARAMS_IOS),
                GankioTabFragment.get(GankioEntity.ResultsEntity.PARAMS_H5)
        ))
    }

    override fun getContentResId(): Int {
        return R.layout.activity_gank_io
    }

    class TabPagerAdapter(fragmentManager: FragmentManager, private val mFragments: List<GankioTabFragment>) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragments[position].getType()
        }
    }
}