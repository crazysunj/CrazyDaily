package com.crazysunj.crazydaily.ui.splash;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.crazysunj.crazydaily.R;

public class SplashActivity extends AppCompatActivity {

    private static String[] FILE_NAMES = {"lottie/C.json", "lottie/R.json", "lottie/A.json", "lottie/Z.json", "lottie/Y.json",
            "lottie/D.json", "lottie/A.json", "lottie/I.json", "lottie/L.json", "lottie/Y.json"};
    private static String[] MTRVA_NAMES = {"lottie/M.json", "lottie/T.json", "lottie/R.json", "lottie/V.json", "lottie/A.json"};
    private FrameLayout mAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAppName = findViewById(R.id.splash_lottie);
        int px = dp2px(this, 50);
        addComposition(0, px);
//        LottieAnimationView view = new LottieAnimationView(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////        view.setImageAssetsFolder("images/test/");
//        view.setAnimation("lottie/data.json");
//        view.loop(true);
//        mAppName.addView(view, params);
//        view.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.d("SplashActivity", "animation.getAnimatedValue():" + animation.getAnimatedValue());
//            }
//        });
//        view.playAnimation();
//        Log.d("SplashActivity", "view.getDuration():" + view.getDuration());
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void addComposition(int index, int px) {
        final int length = MTRVA_NAMES.length;
        if (index == length) {
//            Observable.timer(2, TimeUnit.SECONDS, Schedulers.computation())
//                    .subscribe(l -> {
//                        HomeActivity.start(this);
//                        finish();
//                    });
            return;
        }
        LottieAnimationView view = new LottieAnimationView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (index != 0) {
            params.leftMargin = index * px;
        }
        view.setAnimation(MTRVA_NAMES[index]);
        mAppName.addView(view, params);
        view.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                addComposition(index + 1, px);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        view.playAnimation();
    }
}
