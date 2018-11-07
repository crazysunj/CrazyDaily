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
package com.crazysunj.crazydaily.view.banner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;
import com.crazysunj.cardslideview.ElasticCardView;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.ui.zhihu.ZhihuNewsDetailActivity;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsEntity;

/**
 * @author: sunjian
 * created on: 2017/9/22 下午1:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class BannerCardHandler implements CardHandler<ZhihuNewsEntity.TopStoriesEntity> {

    @Override
    public View onBind(final Context context, final ZhihuNewsEntity.TopStoriesEntity data, final int position, @CardViewPager.TransformerMode int mode) {
        View view = View.inflate(context, R.layout.item_banner, null);
        ImageView bg = (ImageView) view.findViewById(R.id.banner_bg);
        ElasticCardView cardView = (ElasticCardView) view.findViewById(R.id.banner_cv);
        TextView title = (TextView) view.findViewById(R.id.banner_title);
        title.setText(data.getTitle());
        final boolean isCard = mode == CardViewPager.MODE_CARD;
        cardView.setPreventCornerOverlap(isCard);
        cardView.setUseCompatPadding(isCard);
        ImageLoader.load(context, data.getImage(), bg);
        view.setOnClickListener(v -> ZhihuNewsDetailActivity.start((Activity) v.getContext(), data.getId(), bg));
        return view;
    }
}
