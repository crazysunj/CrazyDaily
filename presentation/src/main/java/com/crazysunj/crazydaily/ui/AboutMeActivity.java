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
package com.crazysunj.crazydaily.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.util.SnackbarUtil;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2017/9/10 下午5:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.about_me_github)
    TextView mGithub;
    @BindView(R.id.about_me_blog)
    TextView mBlog;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutMeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
        mGithub.setOnLongClickListener(v -> {
            String github = mGithub.getText().toString().trim();
            copyContent(github);
            return false;
        });

        mBlog.setOnLongClickListener(v -> {
            String blog = mBlog.getText().toString().trim();
            copyContent(blog);
            return false;
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initInject() {

    }

    private void copyContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(content);
            SnackbarUtil.show(this, "已经复制到剪切板！");
        }
    }
}
