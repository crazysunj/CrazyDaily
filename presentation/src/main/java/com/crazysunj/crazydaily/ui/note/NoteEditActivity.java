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
package com.crazysunj.crazydaily.ui.note;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.base.BaseViewHolder;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.extension.ItemTouchHelperExtension;
import com.crazysunj.crazydaily.presenter.NoteEditPresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.crazydaily.ui.adapter.NoteEditAdapter;
import com.crazysunj.crazydaily.ui.photo.PhotoPickerActivity;
import com.crazysunj.crazydaily.util.DeviceUtil;
import com.crazysunj.crazydaily.util.ScreenUtil;
import com.crazysunj.crazydaily.view.dialog.CrazyDailyAlertDialog;
import com.crazysunj.crazydaily.view.note.NoteEditText;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.jaeger.library.StatusBarUtil;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2018/9/27 下午3:56
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteEditActivity extends BaseActivity<NoteEditPresenter> implements NoteEditContract.View {

    public static final int REQUEST_CODE = 3;
    public static final int RESULT_CREATE_CODE = 3;
    public static final int RESULT_EDIT_CODE = 4;

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
    private Long mSaveId = null;
    private NoteEntity mEditNote;

    public static void start(Fragment fragment) {
        start(fragment, null);
    }

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Fragment fragment, NoteEntity note) {
        Intent intent = new Intent(fragment.getContext(), NoteEditActivity.class);
        intent.putExtra(ActivityConstant.DATA, note);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    public static void start(Activity activity, NoteEntity note) {
        Intent intent = new Intent(activity, NoteEditActivity.class);
        intent.putExtra(ActivityConstant.DATA, note);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mDragDelete.setTranslationY(getResources().getDimensionPixelSize(R.dimen.space_50));
        mDragDeleteIcon.setColorFilter(Color.WHITE);
        List<String> images = new ArrayList<>();
        images.add(null);
        mImages.setLayoutManager(new GridLayoutManager(this, 3));
        ItemTouchHelperExtension itemTouchHelper = new ItemTouchHelperExtension(new NoteEditItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(mImages);
        mAdapter = new NoteEditAdapter(images);
        mImages.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mEditNote = getIntent().getParcelableExtra(ActivityConstant.DATA);
        if (mEditNote == null) {
            mPresenter.getNote();
        } else {
            showNote(mEditNote);
            mSubmitBtn.setEnabled(true);
        }
    }

    @Override
    protected void initListener() {
        mCancel.setOnClickListener(v -> showSaveDialog());
        mSubmit.setOnClickListener(v -> {
            if (!mSubmitBtn.isEnabled()) {
                return;
            }
            final String text = mEdit.getText();
            mPresenter.submitNote(new NoteEntity(mSaveId == null ? System.currentTimeMillis() : mSaveId, mAdapter.getImages(), text, mImageDownload.isChecked(), NoteEntity.FLAG_SUBMIT));
        });
        mAdapter.setOnItemDeleteListener(this::removeImage);
        mAdapter.setOnItemSelectListener(selectCount -> PhotoPickerActivity.start(this, selectCount));
        //noinspection unchecked
        mAdapter.setOnItemClickListener((position, data, view) -> NotePreviewActivity.start(this, position, data, Pair.<View, String>create(view, getString(R.string.transition_note_preview_image))));
        mEdit.setOnEditTextListener(new NoteEditText.OnEditTextListener() {
            @Override
            public void onShowSoftKeyBoard() {
                Rect r = new Rect();
                final View decorView = getWindow().getDecorView();
                decorView.getWindowVisibleDisplayFrame(r);
                int heightPixels = getResources().getDisplayMetrics().heightPixels;
                int decorViewHeight = decorView.getHeight();
                final int bottom = r.bottom;
                boolean isNoVirtualNavigation = DeviceUtil.isNoVirtualNavigation(NoteEditActivity.this);
                int heightDifference;
                if (!isNoVirtualNavigation && bottom == heightPixels) {
                    heightDifference = 0;
                } else if (isNoVirtualNavigation && bottom == decorViewHeight) {
                    heightDifference = 0;
                } else {
                    heightDifference = decorViewHeight - bottom;
                }
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
                mSubmitBtn.setEnabled(length > 0 && !mAdapter.getImages().isEmpty());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showSaveDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showSaveDialog() {
        if (!mSubmitBtn.isEnabled() || mEditNote != null) {
            finish();
            return;
        }
        new CrazyDailyAlertDialog.Builder()
                .setMessgae("将此次笔记保存？")
                .setNegative("不保存")
                .setOnNegativeClickListener(v -> {
                    if (mSaveId == null) {
                        cancelSuccess();
                    } else {
                        mPresenter.cancelNote(mSaveId);
                    }
                })
                .setPositive("保存")
                .setPositiveColor(Color.parseColor("#F93450"))
                .setOnPositiveClickListener(v -> {
                    final String text = mEdit.getText();
                    mPresenter.saveNote(new NoteEntity(mSaveId == null ? System.currentTimeMillis() : mSaveId, mAdapter.getImages(), text, mImageDownload.isChecked(), NoteEntity.FLAG_DRAFT));
                })
                .build()
                .show(this);
    }

    private void removeImage(int position) {
        mAdapter.removeImage(position);
        if (mAdapter.isMaxRemoveImageSize(PhotoPickerActivity.MAX_SELECT_NUMBER - 1)) {
            mAdapter.addPhotoAddItem();
        }
        if (mAdapter.isMinImageSize()) {
            mSubmitBtn.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PhotoPickerActivity.REQUEST_CODE == requestCode && PhotoPickerActivity.RESULT_CODE == resultCode && data != null) {
            String[] images = data.getStringArrayExtra(ActivityConstant.IMAGES);
            appendImages(Arrays.asList(images));
        } else if (NotePreviewActivity.REQUEST_CODE == requestCode && NotePreviewActivity.RESULT_CODE == resultCode && data != null) {
            List<String> images = data.getStringArrayListExtra(ActivityConstant.DATA);
            handleRemoveResult(images);
        }
    }

    private void handleRemoveResult(List<String> images) {
        mAdapter.resetData(images, PhotoPickerActivity.MAX_SELECT_NUMBER);
        if (mAdapter.isMinImageSize()) {
            mSubmitBtn.setEnabled(false);
        }
    }

    private void appendImages(List<String> images) {
        mAdapter.appendImage(images);
        if (mAdapter.isMaxAddImageSize(PhotoPickerActivity.MAX_SELECT_NUMBER + 1)) {
            mAdapter.removePhotoAddItem();
        }
        mSubmitBtn.setEnabled(!images.isEmpty() && mEdit.getText().length() > 0);
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

    private void hideCanDragDelete() {
        mDragDelete.setBackgroundColor(Color.parseColor("#CC7BD4FB"));
        mDragDeleteIcon.setImageResource(R.mipmap.ic_delete_unopen);
        mDragDeleteText.setText("拖拽到此处删除");
    }

    @SuppressLint("SetTextI18n")
    private void showSoftKeyBoard(int heightDifference) {
        if (heightDifference > 0) {
            if (mSoftKeyBoardView == null) {
                mSoftKeyBoardView = (TextView) View.inflate(this, R.layout.layout_soft_keyboard, null);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dp2px(this, 36));
                params.gravity = Gravity.BOTTOM;
                params.bottomMargin = heightDifference;
                ((FrameLayout) getWindow().getDecorView()).addView(mSoftKeyBoardView, params);
                int length = mEdit.getText().length();
                mSoftKeyBoardView.setText(length + "/200");
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mSoftKeyBoardView.getLayoutParams();
            if (params.bottomMargin != heightDifference) {
                params.bottomMargin = heightDifference;
                mSoftKeyBoardView.setLayoutParams(params);
            }
            mSoftKeyBoardView.setVisibility(View.VISIBLE);
        } else {
            if (mSoftKeyBoardView != null) {
                mSoftKeyBoardView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent()
                .inject(this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_note_edit;
    }

    @Override
    public void showNote(NoteEntity noteEntity) {
        List<String> images = noteEntity.getImages();
        appendImages(images);
        mEdit.setText(noteEntity.getText());
        mImageDownload.setChecked(noteEntity.getIsCanDownload());
        mSaveId = noteEntity.getId();
    }

    @Override
    public void cancelSuccess() {
        finish();
    }

    @Override
    public void saveSuccess() {
        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void submitSuccess(NoteEntity noteEntity) {
        Intent intent = new Intent();
        intent.putExtra(ActivityConstant.DATA, noteEntity);
        if (mEditNote == null) {
            Toast.makeText(getApplicationContext(), "发布成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CREATE_CODE, intent);
        } else {
            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_EDIT_CODE, intent);
        }
        finish();
    }

    private class NoteEditItemTouchHelperCallback extends ItemTouchHelperExtension.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            NoteEditAdapter adapter = (NoteEditAdapter) recyclerView.getAdapter();
            int position = viewHolder.getLayoutPosition();
            final int dragFlags = ItemTouchHelperExtension.LEFT | ItemTouchHelperExtension.UP | ItemTouchHelperExtension.RIGHT | ItemTouchHelperExtension.DOWN;
            assert adapter != null;
            return makeMovementFlags(adapter.isPhotoAddItem(position) ? 0 : dragFlags, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            NoteEditAdapter adapter = (NoteEditAdapter) recyclerView.getAdapter();
            assert adapter != null;
            int targetPosition = target.getLayoutPosition();
            if (adapter.isPhotoAddItem(targetPosition)) {
                return false;
            }
            int dragPosition = viewHolder.getLayoutPosition();
            adapter.moveImage(dragPosition, targetPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void itemSelectMove(MotionEvent event, RecyclerView.ViewHolder holder) {
            final int action = MotionEventCompat.getActionMasked(event);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    showDragDelete();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Rect moveRect = new Rect();
                    mDragDelete.getGlobalVisibleRect(moveRect);
                    Rect itemMoveRect = new Rect();
                    ((BaseViewHolder) holder).getView(R.id.item_note_edit_image).getGlobalVisibleRect(itemMoveRect);
                    if (Math.max(itemMoveRect.top, moveRect.top) < Math.min(itemMoveRect.bottom, moveRect.bottom)) {
                        showCanDragDelete();
                    } else {
                        hideCanDragDelete();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Rect upRect = new Rect();
                    mDragDelete.getGlobalVisibleRect(upRect);
                    Rect itemUpRect = new Rect();
                    ((BaseViewHolder) holder).getView(R.id.item_note_edit_image).getGlobalVisibleRect(itemUpRect);
                    hideDragDelete();
                    if (Math.max(itemUpRect.top, upRect.top) < Math.min(itemUpRect.bottom, upRect.bottom)) {
                        removeImage(holder.getLayoutPosition());
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
