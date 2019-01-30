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
package com.crazysunj.crazydaily.ui.splash;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.app.App;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.SplashPresenter;
import com.crazysunj.crazydaily.presenter.contract.SplashContract;
import com.crazysunj.crazydaily.ui.MainActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2018/4/16 下午6:26
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.splash_lottie)
    LottieAnimationView mSplashAnim;

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.sAppState = 0;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initListener() {
        mSplashAnim.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mPresenter.enterHome();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        getActivityComponent()
                .inject(this);
    }

    @Override
    public void enterHome() {
        MainActivity.start(this);
        mSplashAnim.removeAllAnimatorListeners();
        finish();
    }
}
