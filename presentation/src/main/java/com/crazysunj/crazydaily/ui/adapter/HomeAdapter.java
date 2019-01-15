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
package com.crazysunj.crazydaily.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseHelperAdapter;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.di.module.EntityModule;
import com.crazysunj.crazydaily.entity.ExpandCollapseFooterEntity;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.ui.adapter.helper.HomeAdapterHelper;
import com.crazysunj.crazydaily.ui.browser.BrowserActivity;
import com.crazysunj.crazydaily.ui.zhihu.ZhihuNewsDetailActivity;
import com.crazysunj.crazydaily.util.DateUtil;
import com.crazysunj.crazydaily.util.FileUtil;
import com.crazysunj.crazydaily.util.WeatherUtil;
import com.crazysunj.crazydaily.view.video.NeihanVideoPlayerController;
import com.crazysunj.domain.entity.base.MultiTypeIdEntity;
import com.crazysunj.domain.entity.common.CommonFooterEntity;
import com.crazysunj.domain.entity.common.CommonHeaderEntity;
import com.crazysunj.domain.entity.gankio.GankioEntity;
import com.crazysunj.domain.entity.gaoxiao.GaoxiaoItemEntity;
import com.crazysunj.domain.entity.neihan.NeihanItemEntity;
import com.crazysunj.domain.entity.weather.WeatherRemoteEntity;
import com.crazysunj.domain.entity.weather.WeatherXinZhiEntity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;
import com.crazysunj.multitypeadapter.helper.AdapterHelper;
import com.crazysunj.multitypeadapter.helper.RecyclerViewAdapterHelper;
import com.xiao.nicevideoplayer.NiceVideoPlayer;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class HomeAdapter extends BaseHelperAdapter<MultiTypeIdEntity, BaseViewHolder, HomeAdapterHelper> {

    /**
     * 固定不变的或者直接在Adapter中修改数据源的(如footer)可以用这种方法
     */
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

    @Named(EntityModule.NAME_GAOXIAO)
    @Inject
    CommonFooterEntity mGaoxiaoFooterEntity;
    private NiceVideoPlayer mVideoPlayer;
    private String mRemoveVideoItemId;

    @Inject
    public HomeAdapter(HomeAdapterHelper helper) {
        super(helper);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, @NonNull MultiTypeIdEntity item) {
        switch (item.getItemType()) {
//            case NeihanItemEntity.TYPE_NEIHAN:
//                renderNeihan(holder, (NeihanItemEntity) item);
//                break;
            case GaoxiaoItemEntity.TYPE_GAOXIAO:
                renderGaoxiao(holder, (GaoxiaoItemEntity) item);
                break;
            case WeatherRemoteEntity.WeatherEntity.TYPE_WEATHER:
                renderWeather(holder, (WeatherXinZhiEntity.FinalEntity) item);
                break;
            case GankioEntity.ResultsEntity.TYPE_GANK_IO:
                renderGankio(holder, (GankioEntity.ResultsEntity) item);
                break;
            case ZhihuNewsEntity.StoriesEntity.TYPE_ZHIHU_NEWS:
                renderZhihuNews(holder, (ZhihuNewsEntity.StoriesEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_GANK_IO - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER:
                renderGankioHeader(holder, (CommonHeaderEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_ZHIHU - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER:
            case HomeAdapterHelper.LEVEL_GAOXIAO - RecyclerViewAdapterHelper.HEADER_TYPE_DIFFER:
                renderHeader(holder, (CommonHeaderEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_ZHIHU - RecyclerViewAdapterHelper.FOOTER_TYPE_DIFFER:
            case HomeAdapterHelper.LEVEL_GANK_IO - RecyclerViewAdapterHelper.FOOTER_TYPE_DIFFER:
                renderExpandCollapseFooter(holder, (ExpandCollapseFooterEntity) item);
                break;
            case HomeAdapterHelper.LEVEL_GAOXIAO - RecyclerViewAdapterHelper.FOOTER_TYPE_DIFFER:
                renderFooter(holder, (CommonFooterEntity) item);
                break;
            default:
                break;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (viewType == GaoxiaoItemEntity.TYPE_GAOXIAO) {
            NiceVideoPlayer videoPlayer = new NiceVideoPlayer(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.space_240));
            ((ViewGroup) holder.itemView).addView(videoPlayer, params);
        }
        return holder;
    }

    // *********************** 搞笑视频 ***********************

    private DownloadCallback mDownloadCallback;

    public void setDownloadCallback(DownloadCallback callback) {
        mDownloadCallback = callback;
    }

    public interface DownloadCallback {
        void onDownload(String url);
    }

    private void renderGaoxiao(BaseViewHolder holder, GaoxiaoItemEntity item) {
        CircleImageView avatar = holder.getView(R.id.item_neihan_avatar);
        ImageLoader.load(mContext, item.getAvatar(), R.mipmap.ic_huaji, avatar);
        holder.setText(R.id.item_neihan_name, item.getName());
        holder.setText(R.id.item_neihan_title, item.getTitle());
        ViewGroup wrapView = (ViewGroup) holder.itemView;
        NiceVideoPlayer videoPlayer = (NiceVideoPlayer) wrapView.getChildAt(wrapView.getChildCount() - 1);
        NeihanVideoPlayerController controller = new NeihanVideoPlayerController(mContext);
        controller.setDownloadCallback(url -> {
            if (mDownloadCallback != null) {
                mDownloadCallback.onDownload(url);
            }
        });
        controller.setTitle("");
        controller.setLenght(item.getDuration());
        videoPlayer.setUp(item.getVideoUrl(), null);
        videoPlayer.setController(controller);
        ImageLoader.load(mContext, item.getThumbnail(), R.drawable.img_default, controller.imageView());
    }

    private int getPosition(BaseViewHolder holder) {
        return holder.getAdapterPosition();
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        final int position = getPosition(holder);
        if (position < 0) {
            return;
        }
        final MultiTypeIdEntity item = mHelper.getItem(position);
        if (item.getItemType() == GaoxiaoItemEntity.TYPE_GAOXIAO) {
            ViewGroup wrapView = (ViewGroup) holder.itemView;
            View lastView = wrapView.getChildAt(wrapView.getChildCount() - 1);
            if (!(lastView instanceof NiceVideoPlayer)) {
                NiceVideoPlayer videoPlayer = new NiceVideoPlayer(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.space_240));
                ((ViewGroup) holder.itemView).addView(videoPlayer, params);
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        final int position = getPosition(holder);
        if (position < 0) {
            return;
        }
        final MultiTypeIdEntity item = mHelper.getItem(position);
        if (item.getItemType() == GaoxiaoItemEntity.TYPE_GAOXIAO) {
            GaoxiaoItemEntity gaoxiaoItem = (GaoxiaoItemEntity) item;
            if (gaoxiaoItem.getStringId().equals(mRemoveVideoItemId)) {
                ViewGroup wrapView = (ViewGroup) holder.itemView;
                View lastView = wrapView.getChildAt(wrapView.getChildCount() - 1);
                if (lastView instanceof NiceVideoPlayer) {
                    wrapView.removeView(lastView);
                }
                mVideoPlayer.exitTinyWindow();
                wrapView.addView(mVideoPlayer);
                mVideoPlayer = null;
                mRemoveVideoItemId = null;
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        final int position = getPosition(holder);
        if (position < 0) {
            return;
        }
        final MultiTypeIdEntity item = mHelper.getItem(position);
        if (item.getItemType() == GaoxiaoItemEntity.TYPE_GAOXIAO) {
            ViewGroup wrapView = (ViewGroup) holder.itemView;
            NiceVideoPlayer videoPlayer = (NiceVideoPlayer) wrapView.getChildAt(wrapView.getChildCount() - 1);
            if (videoPlayer.isPlaying()) {
                wrapView.removeView(videoPlayer);
                mVideoPlayer = videoPlayer;
                // 视频封装库关于小窗口位置是写死的，可通过反射重新设置
                // 再不济可以自己封装视频UI，现在基本都是基于ijkplayer
                mVideoPlayer.enterTinyWindow();
                GaoxiaoItemEntity gaoxiaoItem = (GaoxiaoItemEntity) item;
                mRemoveVideoItemId = gaoxiaoItem.getStringId();
            }
        }
    }

    public void notifyGaoxiaoList(List<GaoxiaoItemEntity> data) {
        mRemoveVideoItemId = null;
        final int level = HomeAdapterHelper.LEVEL_GAOXIAO;
        AdapterHelper.with(level)
                .data(data)
                .header(mGaoxiaoHeaderEntity)
                .footer(mGaoxiaoFooterEntity)
                .into(mHelper);
    }

    // *********************** 内涵段子 ***********************

//    private void renderNeihan(BaseViewHolder helper, NeihanItemEntity item) {
//        CircleImageView avatar = helper.getView(R.id.item_neihan_avatar);
//        ImageLoader.load(mContext, item.getAvatar(), R.mipmap.ic_huaji, avatar);
//        helper.setText(R.id.item_neihan_name, item.getName());
//        helper.setText(R.id.item_neihan_title, item.getTitle());
//        NiceVideoPlayer videoPlayer = helper.getView(R.id.item_neihan_video);
//        NeihanVideoPlayerController controller = new NeihanVideoPlayerController(mContext);
//        controller.setTitle("");
//        controller.setLenght(item.getDuration());
//        controller.setClarity(item.getClarityList(), 0);
//        videoPlayer.setController(controller);
//        ImageLoader.load(mContext, item.getThumbnail(), R.drawable.img_default, controller.imageView());
//
//    }


    public void notifyNeihanList(List<NeihanItemEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_NEIHAN;
        AdapterHelper.with(level)
                .data(data)
                .header(mNeihanHeaderEntity)
                .into(mHelper);
    }

    // *********************** Weather ***********************

    private void renderWeather(BaseViewHolder holder, WeatherXinZhiEntity.FinalEntity item) {
        holder.setText(R.id.item_weather_temperature, item.getTemperature());
        String location = item.getLocation();
        holder.setText(R.id.item_weather_location, location);
        holder.setText(R.id.item_weather_text, item.getText());
        holder.setText(R.id.item_weather_time, item.getLastUpdate());
        holder.setImageResource(R.id.item_weather_icon, WeatherUtil.getWeatherIcon(item.getCode()));
        holder.itemView.setOnClickListener(v -> {
            if (mOnHeaderClickListener != null) {
                final int position = holder.getAdapterPosition();
                mOnHeaderClickListener.onHeaderClick(mHelper.getLevel(item.getItemType()),
                        ((WeatherXinZhiEntity.FinalEntity) mHelper.getItem(position)).getLocation());
            }
        });
    }

    public void notifyWeatherEntity(WeatherXinZhiEntity.FinalEntity weatherEntity) {
        final int level = HomeAdapterHelper.LEVEL_WEATHER;
        AdapterHelper.with(level)
                .data(weatherEntity)
                .into(mHelper);
    }

    // *********************** Gankio ***********************

    private void renderGankio(BaseViewHolder holder, GankioEntity.ResultsEntity item) {
        holder.setText(R.id.item_gank_io_title, item.getDesc());
        holder.setText(R.id.item_gank_io_author, String.format("作者：%s", FileUtil.getText(item.getWho(), "神秘大佬")));
        holder.setText(R.id.item_gank_io_date, String.format("发布时间：%s", DateUtil.getLocalTime(item.getPublishedAt())));
        holder.itemView.setOnClickListener(v -> BrowserActivity.start(mContext, item.getUrl()));
    }

    private void renderGankioHeader(BaseViewHolder holder, CommonHeaderEntity item) {
        final int level = mHelper.getLevel(item.getItemType());
        holder.setText(R.id.header_title, item.getTitle());
        TextView optionsView = holder.getView(R.id.header_options);
        final String options = item.getOptions();
        optionsView.setText(options);
        optionsView.setTextColor(HomeAdapterHelper.getColor(level));
        optionsView.setOnClickListener(v -> {
            if (mOnHeaderClickListener != null) {
                final int position = holder.getAdapterPosition();
                CommonHeaderEntity headerEntity = (CommonHeaderEntity) mHelper.getItem(position);
                String headerEntityOptions = headerEntity.getOptions();
                mOnHeaderClickListener.onHeaderClick(level,
                        headerEntityOptions);
            }
        });
    }

    public void notifyGankioList(List<GankioEntity.ResultsEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_GANK_IO;
        final String title = String.format(Locale.getDefault(), "展开（剩余%d个）", data.size() - HomeAdapterHelper.MIN_GANK_IO);
        final String options = data.get(0).getType();
        CommonHeaderEntity headerEntity = new CommonHeaderEntity(options, level, GankioEntity.ResultsEntity.HEADER_TITLE, options);
        mGankioFooterEntity.initStatus(title);
        AdapterHelper.with(level)
                .data(data)
                .header(headerEntity)
                .footer(mGankioFooterEntity)
                .into(mHelper);
    }

    // ***********************知乎 ***********************

    private void renderHeader(BaseViewHolder holder, CommonHeaderEntity item) {
        final int level = mHelper.getLevel(item.getItemType());
        holder.setText(R.id.header_title, item.getTitle());
        TextView optionsView = holder.getView(R.id.header_options);
        final String options = item.getOptions();
        optionsView.setText(options);
        optionsView.setTextColor(HomeAdapterHelper.getColor(level));
        optionsView.setOnClickListener(v -> {
            if (mOnHeaderClickListener != null) {
                final int position = holder.getAdapterPosition();
                CommonHeaderEntity headerEntity = (CommonHeaderEntity) mHelper.getItem(position);
                mOnHeaderClickListener.onHeaderClick(level,
                        headerEntity.getOptions());
            }
        });
    }

    private void renderZhihuNews(BaseViewHolder holder, ZhihuNewsEntity.StoriesEntity item) {
        final AppCompatImageView icon = holder.getView(R.id.item_zhihu_news_icon);
        ImageLoader.load(mContext, getUrl(item.getImages()), icon);
        holder.setText(R.id.item_zhihu_news_title, item.getTitle());
        holder.itemView.setOnClickListener(v -> ZhihuNewsDetailActivity.start((Activity) v.getContext(), item.getId(), icon));
    }

    public void notifyZhihuNewsList(List<ZhihuNewsEntity.StoriesEntity> data) {
        final int level = HomeAdapterHelper.LEVEL_ZHIHU;
        final String title = String.format(Locale.getDefault(), "展开（剩余%d个）", data.size() - HomeAdapterHelper.MIN_ZHIHU);
        mZhihuFooterEntity.initStatus(title);
        AdapterHelper.with(level)
                .data(data)
                .header(mZhihuHeaderEntity)
                .footer(mZhihuFooterEntity)
                .into(mHelper);
    }

    private String getUrl(List<String> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }

    //*********************** 公共Footer ***********************
    private void renderExpandCollapseFooter(BaseViewHolder holder, ExpandCollapseFooterEntity item) {
        String stringId = item.getStringId();
        holder.setText(R.id.footer_title, stringId);
        holder.setImageResource(R.id.footer_icon, item.getIconResId());
        holder.itemView.setOnClickListener(v -> {
            item.switchStatus();
            mHelper.foldType(mHelper.getLevel(item.getItemType()), item.isFlod());
            mHelper.setData(getPosition(holder), item);
        });
    }

    private void renderFooter(BaseViewHolder holder, CommonFooterEntity item) {
        holder.setText(R.id.footer_title, item.getTitle());
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
