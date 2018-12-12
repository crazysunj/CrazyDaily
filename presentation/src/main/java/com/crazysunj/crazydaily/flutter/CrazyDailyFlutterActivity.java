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
 * description:
 */
public class CrazyDailyFlutterActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, CrazyDailyFlutterActivity.class);
        context.startActivity(intent);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        View contentView = Flutter.createView(this, getLifecycle(), "testWidget");
//    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_flutter;
    }

    @Override
    protected void initView() {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.flutter_container, Flutter.createFragment("testWidget"));
        tx.commit();
    }
}
