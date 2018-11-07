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
package com.crazysunj.crazydaily.ui.adapter.helper;

import android.graphics.Color;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.ui.adapter.helper.callback.StringDiffCallBack;
import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.weather.WeatherRemoteEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.multitypeadapter.helper.AsynAdapterHelper;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.DiffUtil;

/**
 * @author: sunjian
 * created on: 2017/9/10 下午5:03
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class HomeAdapterHelper extends AsynAdapterHelper<MultiTypeIdEntity> {

    public static final int MIN_ZHIHU = 2;
    public static final int MIN_GANK_IO = 3;

    public static final int LEVEL_ZHIHU = 0;
    public static final int LEVEL_GANK_IO = 1;
    public static final int LEVEL_WEATHER = 2;
    public static final int LEVEL_NEIHAN = 3;
    public static final int LEVEL_GAOXIAO = 4;

    @Inject
    public HomeAdapterHelper() {
        super(null);
    }

    @Override
    protected void registerModule() {
        registerModule(LEVEL_ZHIHU)
                .type(ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS)
                .layoutResId(R.layout.item_zhihu_news)
                .headerResId(R.layout.header_common)
                .footerResId(R.layout.footer_common)
                .minSize(MIN_ZHIHU)
                .isFolded(true)
                .register();

        registerModule(LEVEL_GANK_IO)
                .type(GankioEntity.ResultsEntity.TYPE_GANK_IO)
                .layoutResId(R.layout.item_gank_io)
                .headerResId(R.layout.header_common)
                .footerResId(R.layout.footer_common)
                .minSize(MIN_GANK_IO)
                .isFolded(true)
                .register();

        registerModule(LEVEL_WEATHER)
                .type(WeatherRemoteEntity.WeatherEntity.TYPE_WEATHER)
                .layoutResId(R.layout.item_weather)
                .register();

        registerModule(LEVEL_GAOXIAO)
                .type(GaoxiaoItemEntity.TYPE_GAOXIAO)
                .layoutResId(R.layout.item_neihan)
                .headerResId(R.layout.header_common)
                .footerResId(R.layout.footer_bottom_line)
                .register();
    }

    public static int getColor(int level) {
        switch (level) {
            case LEVEL_ZHIHU:
                return Color.parseColor("#BEE7E9");
            case LEVEL_GANK_IO:
                return Color.parseColor("#19CAAD");
            case LEVEL_GAOXIAO:
                return Color.parseColor("#FF5C8D");
            default:
                return Color.parseColor("#19CAAD");
        }
    }

    @Override
    protected DiffUtil.Callback getDiffCallBack(List<MultiTypeIdEntity> oldData, List<MultiTypeIdEntity> newData) {
        return new StringDiffCallBack(oldData, newData);
    }
}
