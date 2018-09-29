package com.crazysunj.crazydaily.ui.note;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.NoteEditPresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.crazydaily.view.note.NoteEditText;
import com.crazysunj.domain.entity.note.NoteEntity;

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

    private View mSoftKeyBoardView;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoteEditActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initListener() {
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
        });
    }

    private void showSoftKeyBoard(int heightDifference) {
        if (heightDifference > 0) {
            if (mSoftKeyBoardView == null) {
                mSoftKeyBoardView = View.inflate(this, R.layout.layout_soft_keyboard, null);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
