package com.crazysunj.crazydaily.view.banner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;
import com.crazysunj.cardslideview.ElasticCardView;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.ui.ZhihuNewsDetailActivity;
import com.crazysunj.domain.entity.ZhihuNewsEntity;

/**
 * description
 * <p>
 * Created by sunjian on 2017/6/24.
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
        Glide.with(context).load(data.getImage()).into(bg);
        view.setOnClickListener(v -> ZhihuNewsDetailActivity.start((Activity) v.getContext(), data.getId(), bg));
        return view;
    }
}
