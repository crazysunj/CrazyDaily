package com.crazysunj.crazydaily.ui.adapter.helper;

import android.graphics.Color;
import android.support.v7.util.DiffUtil;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.ui.adapter.helper.callback.StringDiffCallBack;
import com.crazysunj.domain.entity.GankioEntity;
import com.crazysunj.domain.entity.NeihanItemEntity;
import com.crazysunj.domain.entity.WeatherRemoteEntity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;
import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.multitypeadapter.helper.AsynAdapterHelper;

import java.util.List;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2017/9/10 下午5:03
 * description:
 */

public class HomeAdapterHelper extends AsynAdapterHelper<MultiTypeIdEntity, BaseAdapter> {

    public static final int MIN_ZHIHU = 2;
    public static final int MIN_GANK_IO = 3;

    @Inject
    public HomeAdapterHelper() {
        super(null);
    }

    @Override
    protected void registerMoudle() {
        registerMoudle(ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS)
                .level(0)
                .headerResId(R.layout.header_common)
                .layoutResId(R.layout.item_zhihu_news)
                .footerResId(R.layout.footer_common)
                .minSize(MIN_ZHIHU)
                .isFolded(true)
                .register();

        registerMoudle(GankioEntity.ResultsEntity.TYPE_GANK_IO)
                .level(1)
                .headerResId(R.layout.header_common)
                .layoutResId(R.layout.item_gank_io)
                .footerResId(R.layout.footer_common)
                .minSize(MIN_GANK_IO)
                .isFolded(true)
                .register();

        registerMoudle(WeatherRemoteEntity.WeatherEntity.TYPE_WEATHER)
                .level(2)
                .layoutResId(R.layout.item_weather)
                .register();

        registerMoudle(NeihanItemEntity.TYPE_NEIHAN)
                .level(3)
                .headerResId(R.layout.header_common)
                .layoutResId(R.layout.item_neihan)
                .register();
    }

    public static int getColor(int type) {
        switch (type) {
            case ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS:
                return Color.parseColor("#BEE7E9");
            case GankioEntity.ResultsEntity.TYPE_GANK_IO:
                return Color.parseColor("#19CAAD");
            case NeihanItemEntity.TYPE_NEIHAN:
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
