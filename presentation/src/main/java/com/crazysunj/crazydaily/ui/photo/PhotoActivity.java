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
package com.crazysunj.crazydaily.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.moudle.ImageLoader;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/4/28 下午1:41
 * description:https://github.com/crazysunj/CrazyDaily
 */
public class PhotoActivity extends BaseActivity {

    @BindView(R.id.photo)
    PhotoView mPhoto;

    public static void start(Activity activity, String url, View view) {
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putExtra(ActivityConstant.URL, url);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transition_photo));
        activity.startActivity(intent, options.toBundle());
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra(ActivityConstant.URL);
        ImageLoader.load(this, url, mPhoto);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_photo;
    }
}
