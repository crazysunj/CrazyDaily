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

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.crazysunj.crazydaily.R
import com.crazysunj.crazydaily.base.BaseAdapter
import com.crazysunj.crazydaily.base.BaseFragment
import com.crazysunj.crazydaily.base.BaseViewHolder
import com.crazysunj.crazydaily.presenter.GankioTabPresenter
import com.crazysunj.crazydaily.presenter.contract.GankioTabContract
import com.crazysunj.crazydaily.ui.browser.BrowserActivity
import com.crazysunj.crazydaily.util.DateUtil
import com.crazysunj.domain.entity.gankio.GankioEntity
import kotlinx.android.synthetic.main.fragemnt_tab_gank_io.*

/**
 * @author: sunjian
 * created on: 2018/12/28 下午3:54
 * description: https://github.com/crazysunj/CrazyDaily
 */
class GankioTabFragment : BaseFragment<GankioTabPresenter>(), GankioTabContract.View {

    companion object {
        fun get(type: String): GankioTabFragment {
            val args = Bundle()
            args.putString("type", type)
            val fragment = GankioTabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mAdapter: GankioTabAdapter? = null
    override fun showGankio(gankioList: List<GankioEntity.ResultsEntity>) {
        mAdapter?.refreshData(gankioList)
    }

    override fun initView() {
        mAdapter = GankioTabAdapter()
        gank_io_tab_list.adapter = mAdapter
        gank_io_tab_list.layoutManager = LinearLayoutManager(mActivity)
    }

    override fun initData() {
        mPresenter.getGankioList(getType())
    }

    override fun getContentResId(): Int {
        return R.layout.fragemnt_tab_gank_io
    }

    override fun initInject() {
        fragmentComponent.inject(this)
    }

    fun getType(): String {
        return arguments?.getString("type", GankioEntity.ResultsEntity.PARAMS_ANDROID)
                ?: GankioEntity.ResultsEntity.PARAMS_ANDROID
    }

    class GankioTabAdapter : BaseAdapter<GankioEntity.ResultsEntity, BaseViewHolder>(R.layout.item_gank_io) {

        fun refreshData(data: List<GankioEntity.ResultsEntity>) {
            mData = data
            notifyDataSetChanged()
        }

        override fun convert(holder: BaseViewHolder, item: GankioEntity.ResultsEntity) {
            holder.setText(R.id.item_gank_io_title, item.desc ?: "")
            holder.setText(R.id.item_gank_io_author, "作者：${item.who ?: "神秘大佬"}")
            holder.setText(R.id.item_gank_io_date, "发布时间：${DateUtil.getLocalTime(item.publishedAt)}")
            holder.itemView.setOnClickListener { BrowserActivity.start(mContext, item.url) }
        }
    }
}