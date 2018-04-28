/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crazysunj.crazydaily.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseAdapter;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.entity.ExpandCollapseFooterEntity;
import com.crazysunj.crazydaily.moudle.ImageLoader;
import com.crazysunj.crazydaily.ui.BrowserActivity;
import com.crazysunj.crazydaily.ui.ZhihuNewsDetailActivity;
import com.crazysunj.crazydaily.ui.adapter.helper.HomeAdapterHelper;
import com.crazysunj.crazydaily.util.DateUtil;
import com.crazysunj.crazydaily.util.StringUtil;
import com.crazysunj.crazydaily.util.WeatherUtil;
import com.crazysunj.crazydaily.view.video.NeihanVideoPlayerController;
import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.domain.entity.common.CommonHeaderEntity;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.weather.WeatherRemoteEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */

public class HomeAdapter extends BaseAdapter<MultiTypeIdEntity, BaseViewHolder, HomeAdapterHelper> {

    //固定不变的或者直接在Adapter中修改数据源的(如footer)可以用这种方法
    @Named(EntityModule.NAME_ZHIHU)
    @Inject
    CommonHeaderEntity mZhihuHeaderEntity;

    @Named(EntityModule.NAME_NEIHAN)
    @Inject
    CommonHeaderEntity mNeihanHeaderEntity;

    @Named(EntityModule.NAME_GAOXIAO)
    @Inject
    CommonHeaderEntity mGaoxiaoHeaderEntity;

    @Named(EntityModule.NAME_ZHIHU)
    @Inject
    ExpandCollapseFooterEntity mZhihuFooterEntity;

    @Named(EntityModule.NAME_GANK_IO)
    @Inject
    ExpandCollapseFooterEntity mGankioFooterEntity;

    @Inject
    public HomeAdapter(HomeAdapterHelper helper) {
        super(helper);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiTypeIdEntity item) {
        switch (item.getItemType()) {
//            case NeihanItemEntity.TYPE_NEIHAN:
//                renderNeihan(helper, (NeihanItemEntity) item);
//                break;
            case GaoxiaoItemEntity.TYPE_GAOXIAO:
                renderGaoxiao(helper, (GaoxiaoItemEntity) item);
                break;
            case WeatherRemoteEntity.WeatherEntity.TYPE_WEATHER:
                renderWeather(helper, (WeatherRemoteEntity.WeatherEntity) item);
                break;
            case GankioEntity.ResultsEntity.TYPE_GANK_IO:
                renderGankio(helper, (GankioEntity.ResultsEntity) item);
                break;
            case ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS:
                renderZhihuNews(helper, (ZhihuNewsEntity.StoriesEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_GANK_IO - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER:
                renderGankioHeader(helper, (CommonHeaderEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_ZHIHU - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER:
            case HomeAdapterHelper.LEVEL_GAOXIAO - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER:
                renderHeader(helper, (CommonHeaderEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_ZHIHU - RecyclerViewAdapterHelper.FOOTER_TYPE_DIFFER:
            case HomeAdapterHelper.LEVEL_GANK_IO - RecyclerViewAdapterHelper.FOOTER_TYPE_DIFFER:
                renderFooter(helper, (ExpandCollapseFooterEntity) item);
                break;
            default:
                break;
        }
    }

    // *********************** 搞笑视频 ***********************

    private void renderGaoxiao(BaseViewHolder helper, GaoxiaoItemEntity item) {
        CircleImageView avatar = helper.getView(R.id.item_neihan_avatar);
        ImageLoader.load(mContext, item.getAvatar(), R.mipmap.ic_huaji, avatar);
        helper.setText(R.id.item_neihan_name, item.getName());
        helper.setText(R.id.item_neihan_title, item.getTitle());
        NiceVideoPlayer videoPlayer = helper.getView(R.id.item_neihan_video);
        NeihanVideoPlayerController controller = new NeihanVideoPlayerController(mContext);
        controller.setTitle("");
        controller.setLenght(item.getDuration());
        videoPlayer.setUp(item.getVideoUrl(), null);
        videoPlayer.setController(controller);
        ImageLoader.load(mContext, item.getThumbnail(), R.drawable.img_default, controller.imageView());

    }


    public void notifyGaoxiaoList(List<GaoxiaoItemEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_GAOXIAO;
        mHelper.notifyMoudleDataAndHeaderChanged(data, mGaoxiaoHeaderEntity, level);
    }

    // *********************** 内涵段子 ***********************

    private void renderNeihan(BaseViewHolder helper, NeihanItemEntity item) {
        CircleImageView avatar = helper.getView(R.id.item_neihan_avatar);
        ImageLoader.load(mContext, item.getAvatar(), R.mipmap.ic_huaji, avatar);
        helper.setText(R.id.item_neihan_name, item.getName());
        helper.setText(R.id.item_neihan_title, item.getTitle());
        NiceVideoPlayer videoPlayer = helper.getView(R.id.item_neihan_video);
        NeihanVideoPlayerController controller = new NeihanVideoPlayerController(mContext);
        controller.setTitle("");
        controller.setLenght(item.getDuration());
        controller.setClarity(item.getClarityList(), 0);
        videoPlayer.setController(controller);
        ImageLoader.load(mContext, item.getThumbnail(), R.drawable.img_default, controller.imageView());

    }


    public void notifyNeihanList(List<NeihanItemEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_NEIHAN;
        mHelper.notifyMoudleDataAndHeaderChanged(data, mNeihanHeaderEntity, level);
    }

    // *********************** Weather ***********************

    private void renderWeather(BaseViewHolder helper, WeatherRemoteEntity.WeatherEntity item) {
        WeatherRemoteEntity.WeatherEntity.NowEntity nowEntity = item.getNow();
        helper.setText(R.id.item_weather_temperature, String.format("%s ℃", nowEntity.getTemperature()));
        final String cityName = item.getCity_name();
        helper.setText(R.id.item_weather_location, cityName);
        helper.setText(R.id.item_weather_text, nowEntity.getText());
        helper.setText(R.id.item_weather_time, DateFormat.format("HH:mm", new Date()));
        helper.setImageResource(R.id.item_weather_icon, WeatherUtil.getWeatherIcon(nowEntity.getCode()));
        helper.itemView.setOnClickListener(v -> {
            if (mOnHeaderClickListener != null) {
                mOnHeaderClickListener.onHeaderClick(mHelper.getLevel(item.getItemType()), cityName);
            }
        });
    }

    public void notifyWeatherList(List<WeatherRemoteEntity.WeatherEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_WEATHER;
        mHelper.notifyMoudleDataChanged(data, level);
    }

    // *********************** Gankio ***********************

    private void renderGankio(BaseViewHolder helper, GankioEntity.ResultsEntity item) {
        helper.setText(R.id.item_gank_io_title, item.getDesc());
        helper.setText(R.id.item_gank_io_author, String.format("作者：%s", StringUtil.getText(item.getWho(), "神秘大佬")));
        helper.setText(R.id.item_gank_io_date, String.format("发布时间：%s", DateUtil.getLocalTime(item.getPublishedAt())));
        helper.itemView.setOnClickListener(v -> BrowserActivity.start(mContext, item.getUrl()));
    }

    private void renderGankioHeader(BaseViewHolder helper, CommonHeaderEntity item) {
        final int level = mHelper.getLevel(item.getItemType());
        helper.setText(R.id.header_title, item.getTitle());
        TextView optionsView = helper.getView(R.id.header_options);
        final String options = item.getOptions();
        optionsView.setText(options);
        optionsView.setTextColor(HomeAdapterHelper.getColor(level));
        optionsView.setOnClickListener(v -> {
            if (mOnHeaderClickListener != null) {
                mOnHeaderClickListener.onHeaderClick(level, options);
            }
        });
    }

    public void notifyGankioList(List<GankioEntity.ResultsEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_GANK_IO;
        final String title = String.format(Locale.getDefault(), "展开（剩余%d个）", data.size() - HomeAdapterHelper.MIN_GANK_IO);
        final String options = data.get(0).getType();
        CommonHeaderEntity headerEntity = new CommonHeaderEntity(options, level, GankioEntity.ResultsEntity.HEADER_TITLE, options);
        mGankioFooterEntity.initStatus(title);
        mHelper.notifyMoudleDataAndHeaderAndFooterChanged(headerEntity, data, mGankioFooterEntity, level);
    }

    // ***********************知乎 ***********************

    private void renderHeader(BaseViewHolder helper, CommonHeaderEntity item) {
        final int level = mHelper.getLevel(item.getItemType());
        helper.setText(R.id.header_title, item.getTitle());
        TextView optionsView = helper.getView(R.id.header_options);
        final String options = item.getOptions();
        optionsView.setText(options);
        optionsView.setTextColor(HomeAdapterHelper.getColor(level));
        optionsView.setOnClickListener(v -> {
            if (mOnHeaderClickListener != null) {
                mOnHeaderClickListener.onHeaderClick(level, options);
            }
        });
    }

    private void renderZhihuNews(BaseViewHolder helper, ZhihuNewsEntity.StoriesEntity item) {
        final AppCompatImageView icon = helper.getView(R.id.item_zhihu_news_icon);
        ImageLoader.load(mContext, getUrl(item.getImages()), icon);
        helper.setText(R.id.item_zhihu_news_title, item.getTitle());
        helper.itemView.setOnClickListener(v -> ZhihuNewsDetailActivity.start((Activity) v.getContext(), item.getId(), icon));
    }

    public void notifyZhihuNewsList(List<ZhihuNewsEntity.StoriesEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_ZHIHU;
        final String title = String.format(Locale.getDefault(), "展开（剩余%d个）", data.size() - HomeAdapterHelper.MIN_ZHIHU);
        mZhihuFooterEntity.initStatus(title);
        mHelper.notifyMoudleDataAndHeaderAndFooterChanged(mZhihuHeaderEntity, data, mZhihuFooterEntity, level);
    }

    private String getUrl(List<String> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }

    //*********************** 公共Footer ***********************
    private void renderFooter(BaseViewHolder helper, ExpandCollapseFooterEntity item) {
        String stringId = item.getStringId();
        helper.setText(R.id.footer_title, stringId);
        helper.setImageResource(R.id.footer_icon, item.getIconResId());
        helper.itemView.setOnClickListener(v -> {
            item.switchStatus();
            mHelper.foldType(mHelper.getLevel(item.getItemType()), item.isFlod());
            mHelper.setData(mData.indexOf(item), item);
        });
    }


    //*********************** 接口 ***********************

    private OnHeaderClickListener mOnHeaderClickListener;

    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        mOnHeaderClickListener = listener;
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(int level, String options);
    }
}
