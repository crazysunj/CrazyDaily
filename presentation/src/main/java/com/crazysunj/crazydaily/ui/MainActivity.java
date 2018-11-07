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
package com.crazysunj.crazydaily.ui;

import android.content.Context;
import android.content.Intent;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.MainPresenter;
import com.crazysunj.crazydaily.presenter.contract.MainContract;
import com.crazysunj.crazydaily.ui.home.HomeActivity;

/**
 * @author: sunjian
 * created on: 2018/11/7 下午8:10
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }
}
