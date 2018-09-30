package com.crazysunj.crazydaily.ui.note;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.presenter.NoteEditPresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.crazydaily.ui.adapter.NoteEditAdapter;
import com.crazysunj.crazydaily.ui.photo.PhotoPickerActivity;
import com.crazysunj.crazydaily.util.ScreenUtil;
import com.crazysunj.crazydaily.view.note.NoteEditText;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/9/27 下午3:56
 * description:
 */
public class NoteEditActivity extends BaseActivity<NoteEditPresenter> implements NoteEditContract.View {

    @BindView(R.id.note_edit_cancel)
    TextView mCancel;
    @BindView(R.id.note_edit_submit_btn)
    TextView mSubmitBtn;
    @BindView(R.id.note_edit_submit)
    FrameLayout mSubmit;
    @BindView(R.id.note_edit_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.note_edit_images)
    RecyclerView mImages;
    @BindView(R.id.note_edit_content_edit)
    NoteEditText mEdit;
    @BindView(R.id.note_edit_can_image_download)
    SwitchButton mImageDownload;
    @BindView(R.id.note_edit_drag_delete)
    View mDragDelete;
    @BindView(R.id.note_edit_drag_delete_icon)
    ImageView mDragDeleteIcon;
    @BindView(R.id.note_edit_drag_delete_text)
    TextView mDragDeleteText;

    private TextView mSoftKeyBoardView;
    private ObjectAnimator mShowDragDeleteAnim;
    private ObjectAnimator mHideDragDeleteAnim;
    private NoteEditAdapter mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoteEditActivity.class);
        context.startActivity(intent);
    }

    boolean testShow = true;

    @Override
    protected void initView() {
        mDragDelete.setTranslationY(getResources().getDimensionPixelSize(R.dimen.space_50));
        List<String> images = new ArrayList<>();
//        final Resources resources = getResources();
//        final int id = R.mipmap.ic_photo_picker_add;
//        images.add(String.format("%s://%s/%s", ContentResolver.SCHEME_ANDROID_RESOURCE,
//                resources.getResourcePackageName(id),
//                String.valueOf(id)));
        images.add(null);
        mImages.setLayoutManager(new GridLayoutManager(this, 3));
//        mImages.addItemDecoration(new GridLayoutSpaceItemDecoration.Builder().setSpaceSize(10).build());
        mAdapter = new NoteEditAdapter(images);
        mImages.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mCancel.setOnClickListener(v -> {
            if (testShow) {
                showDragDelete();
            } else {
                hideDragDelete();
            }
            testShow = !testShow;
        });
        mSubmit.setOnClickListener(v -> {
            showCanDragDelete();
        });
        mAdapter.setOnItemDeleteListener(position -> mAdapter.removeImage(position));
        mAdapter.setOnItemSelectListener(selectCount -> PhotoPickerActivity.start(this, selectCount));
        mEdit.setonEditTextListener(new NoteEditText.onEditTextListener() {
            @Override
            public void onShowSoftKeyBoard() {
                Rect r = new Rect();
                final View decorView = getWindow().getDecorView();
                decorView.getWindowVisibleDisplayFrame(r);
                int screenHeight = decorView.getRootView().getHeight();
                int heightDifference = screenHeight - r.bottom;
                showSoftKeyBoard(heightDifference);
            }

            @Override
            public void onShowInputText(String text) {
                final int length = text.length();
                if (mSoftKeyBoardView != null) {
                    final String count = String.valueOf(length);
                    SpannableStringBuilder ssb = new SpannableStringBuilder(count);
                    ssb.setSpan(new ForegroundColorSpan(length > 200 ? Color.parseColor("#F93450") : Color.parseColor("#B2B2B2")), 0, count.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    ssb.append("/200");
                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#B2B2B2")), count.length() + 1, ssb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSoftKeyBoardView.setText(ssb);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PhotoPickerActivity.REQUEST_CODE == requestCode && PhotoPickerActivity.RESULT_CODE == resultCode && data != null) {
            String[] images = data.getStringArrayExtra(ActivityConstant.IMAGES);
            mAdapter.appendImage(Arrays.asList(images));
        }
    }

    private void showDragDelete() {
        if (mHideDragDeleteAnim != null && mHideDragDeleteAnim.isRunning()) {
            mHideDragDeleteAnim.cancel();
        }
        if (mShowDragDeleteAnim == null) {
            mShowDragDeleteAnim = ObjectAnimator.ofFloat(mDragDelete, "translationY", mDragDelete.getTranslationY(), 0);
            mShowDragDeleteAnim.setDuration(100);
            mShowDragDeleteAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mDragDelete.setBackgroundColor(Color.parseColor("#CC7BD4FB"));
                    mDragDeleteIcon.setImageResource(R.mipmap.ic_delete_unopen);
                    mDragDeleteText.setText("拖拽到此处删除");
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mShowDragDeleteAnim.start();
    }

    private void hideDragDelete() {
        if (mShowDragDeleteAnim != null && mShowDragDeleteAnim.isRunning()) {
            mShowDragDeleteAnim.cancel();
        }
        if (mHideDragDeleteAnim == null) {
            mHideDragDeleteAnim = ObjectAnimator.ofFloat(mDragDelete, "translationY", mDragDelete.getTranslationY(), getResources().getDimensionPixelSize(R.dimen.space_50));
            mHideDragDeleteAnim.setDuration(100);
        }
        mHideDragDeleteAnim.start();
    }

    private void showCanDragDelete() {
        mDragDelete.setBackgroundColor(Color.parseColor("#CC33A3DC"));
        mDragDeleteIcon.setImageResource(R.mipmap.ic_delete_open);
        mDragDeleteText.setText("松手即可删除");
    }

    private void showSoftKeyBoard(int heightDifference) {
        if (heightDifference > 0) {
            if (mSoftKeyBoardView == null) {
                mSoftKeyBoardView = (TextView) View.inflate(this, R.layout.layout_soft_keyboard, null);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(this, 36));
                params.gravity = Gravity.BOTTOM;
                params.bottomMargin = heightDifference;
                ((FrameLayout) getWindow().getDecorView()).addView(mSoftKeyBoardView, params);
            }
            mSoftKeyBoardView.setVisibility(View.VISIBLE);
        } else {
            if (mSoftKeyBoardView != null) {
                mSoftKeyBoardView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_note_edit;
    }

    @Override
    public void showTemNote(NoteEntity noteEntity) {

    }
}
