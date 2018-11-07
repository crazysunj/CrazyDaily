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
package com.crazysunj.crazydaily.ui.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.transition.AutoTransition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.module.animation.BezierTransition;
import com.crazysunj.crazydaily.module.animation.CircularRevealTransition;
import com.crazysunj.crazydaily.module.image.ImageLoader;
import com.crazysunj.crazydaily.util.SnackbarUtil;
import com.crazysunj.crazydaily.view.contact.CoolBGView;
import com.crazysunj.crazydaily.view.contact.ImgResEntity;
import com.crazysunj.crazydaily.view.contact.SatelliteMenuView;
import com.crazysunj.domain.entity.contact.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: sunjian
 * created on: 2018/4/16 下午6:26
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class ContactDetailActivity extends BaseActivity {

    private static final String BITMAP = "bitmap";
    private static final String CONTACT = "crazydailyicon";
    @BindView(R.id.satellite_menu)
    SatelliteMenuView mSatelliteMenu;
    @BindView(R.id.cool_bg_view)
    CoolBGView mCoolBGView;
    @BindView(R.id.tx_location)
    TextView mLocation;
    @BindView(R.id.tx_name)
    TextView mName;
    @BindView(R.id.ic_head)
    CircleImageView mHead;

    private Contact mContact;

    public static void start(Activity activity, Bitmap bitmap, Contact contact, Pair<View, String>... pairs) {
        Intent intent = new Intent(activity, ContactDetailActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                (activity, pairs);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        intent.putExtra(BITMAP, bytes.toByteArray());
        intent.putExtra(CONTACT, contact);
        activity.startActivity(intent, optionsCompat.toBundle());
    }

    @Override
    protected void initView() {
        initTransition();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        byte[] bytes = intent.getByteArrayExtra(BITMAP);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        mCoolBGView.setBitmap(bitmap);

        mContact = intent.getParcelableExtra(CONTACT);
        mLocation.setText(mContact.getLocation());
        mName.setText(mContact.getName());
        ImageLoader.load(this, mContact.getAvatar(), mHead);
        List<ImgResEntity> data = new ArrayList<>();
        Resources resources = getResources();
        data.add(new ImgResEntity(R.mipmap.ic_video, resources.getColor(R.color.color_FF4444)));
        data.add(new ImgResEntity(R.mipmap.ic_edit, resources.getColor(R.color.color_EA66A6)));
        data.add(new ImgResEntity(R.mipmap.ic_msg, resources.getColor(R.color.color_FCAF17)));
        data.add(new ImgResEntity(R.mipmap.ic_call, resources.getColor(R.color.color_45B97C)));
        mSatelliteMenu.setChildImgRes(data);
        mSatelliteMenu.setImgRes(new ImgResEntity(R.mipmap.ic_add, resources.getColor(R.color.colorPrimary)));
    }

    @Override
    protected void initListener() {
        mSatelliteMenu.setOnItemClickListener((v, position) -> satelliteMenuItemClick(mContact, position));
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_contact_detail;
    }

    @Override
    public void onBackPressed() {
        if (mSatelliteMenu.isMenuOpen()) {
            mSatelliteMenu.close();
            return;
        }
        super.onBackPressed();
    }

    private void initTransition() {
        Window window = getWindow();
        TransitionSet set = new TransitionSet();

        AutoTransition autoTransition = new AutoTransition();
        autoTransition.excludeTarget(R.id.ic_head, true);
        autoTransition.addTarget(R.id.tx_name);
        autoTransition.addTarget(R.id.ic_location);
        autoTransition.addTarget(R.id.tx_location);
        autoTransition.setDuration(600);
        autoTransition.setInterpolator(new DecelerateInterpolator());
        set.addTransition(autoTransition);

        BezierTransition bezierTransition = new BezierTransition();
        bezierTransition.addTarget(R.id.ic_head);
        bezierTransition.excludeTarget(R.id.tx_name, true);
        bezierTransition.excludeTarget(R.id.ic_location, true);
        bezierTransition.excludeTarget(R.id.tx_location, true);
        bezierTransition.setDuration(600);
        bezierTransition.setInterpolator(new DecelerateInterpolator());
        set.addTransition(bezierTransition);

        CircularRevealTransition transition = new CircularRevealTransition();
        transition.excludeTarget(android.R.id.statusBarBackground, true);
        window.setEnterTransition(transition);
        window.setReenterTransition(transition);
        window.setReturnTransition(transition);
        window.setExitTransition(transition);

        window.setSharedElementEnterTransition(set);
        window.setSharedElementReturnTransition(set);

    }

    private void satelliteMenuItemClick(Contact contact, int position) {
        switch (position) {
            case 0:
                SnackbarUtil.show(this, "想视频？再等等！");
                break;
            case 1:
                SnackbarUtil.show(this, "编辑页面下一步会写！");
                break;
            case 2:
                enterSms(this, contact.getPhone());
                break;
            case 3:
                enterPhone(this, contact.getPhone());
                break;
            default:
                break;
        }
    }

    private void enterPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    private void enterSms(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        context.startActivity(intent);
    }
}
