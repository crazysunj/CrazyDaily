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
