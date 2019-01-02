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
package com.crazysunj.crazydaily.flutter;

import android.content.Context;
import android.content.Intent;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;

import androidx.fragment.app.FragmentTransaction;
import io.flutter.facade.Flutter;

/**
 * @author: sunjian
 * created on: 2018/12/12 上午11:14
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class CrazyDailyFlutterActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, CrazyDailyFlutterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_flutter;
    }

    @Override
    protected void initView() {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flutter_container, Flutter.createFragment("CrazyDailyFlutter"));
        tx.commit();
    }
}
