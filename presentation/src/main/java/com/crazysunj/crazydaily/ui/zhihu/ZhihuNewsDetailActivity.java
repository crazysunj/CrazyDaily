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
package com.crazysunj.crazydaily.ui.zhihu;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.presenter.ZhihuNewsDetailPresenter;
import com.crazysunj.crazydaily.presenter.contract.ZhihuNewsDetailContract;
import com.crazysunj.crazydaily.util.HtmlUtil;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.web.CrazyDailyWebView;
import com.crazysunj.domain.entity.zhihu.ZhihuNewsDetailEntity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaeger.library.StatusBarUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ZhihuNewsDetailActivity extends BaseActivity<ZhihuNewsDetailPresenter> implements ZhihuNewsDetailContract.View {

    @BindView(R.id.zhihu_news_detail_icon)
    ImageView mIcon;
    @BindView(R.id.zhihu_news_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.zhihu_news_detail_web_container)
    FrameLayout mWebContainer;
    @BindView(R.id.zhihu_news_detail_fab)
    FloatingActionButton mFab;
    @BindView(R.id.zhihu_news_detail_appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.zhihu_news_detail_ctl)
    CollapsingToolbarLayout mBar;

    private long mId;
    private String mIconUrl;

    private boolean mIsImageShow = false;
    private boolean mIsTransitionEnd = false;
    private CrazyDailyWebView mWebView;

    public static void start(Activity activity, long id, View shareView) {
        Intent intent = new Intent(activity, ZhihuNewsDetailActivity.class);
        intent.putExtra(ActivityConstant.ID, id);
        if (shareView != null) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, shareView, "shareView").toBundle());
        } else {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers(); //暂停所有布局、解析、JS
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initView() {
        mId = getIntent().getLongExtra(ActivityConstant.ID, 0L);
        setSupportActionBar(mToolbar);
        showBack(mToolbar);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new CrazyDailyWebView(this);
        mWebContainer.addView(mWebView, params);
        handleTranstion();
    }

    @Override
    protected void initListener() {
        mFab.setOnClickListener(v -> SnackbarUtil.show(this, "喜欢就点个star吧！"));
    }

    @Override
    protected void initData() {
        mPresenter.getZhihuNewsDetail(mId);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_zhihu_news_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showContent(ZhihuNewsDetailEntity zhihuNewsDetailEntity) {
        mIconUrl = zhihuNewsDetailEntity.getImage();
        if (!mIsImageShow && mIsTransitionEnd) {
            ImageLoader.load(this, mIconUrl, mIcon);
        }
        mBar.setTitle(zhihuNewsDetailEntity.getTitle());
        String htmlData = HtmlUtil.createHtmlData(zhihuNewsDetailEntity);
        mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void handleTranstion() {
        (getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                mIsTransitionEnd = true;
                if (!TextUtils.isEmpty(mIconUrl)) {
                    mIsImageShow = true;
                    ImageLoader.load(ZhihuNewsDetailActivity.this, mIconUrl, mIcon);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }
}
