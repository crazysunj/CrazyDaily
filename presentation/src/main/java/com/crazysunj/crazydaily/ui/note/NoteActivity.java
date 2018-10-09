package com.crazysunj.crazydaily.ui.note;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.presenter.NotePresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteContract;
import com.crazysunj.crazydaily.ui.adapter.NoteAdapter;
import com.crazysunj.crazydaily.ui.note.dialog.NoteEditDialog;
import com.crazysunj.domain.entity.note.NoteEntity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/10/8 下午4:25
 * description:
 */
public class NoteActivity extends BaseActivity<NotePresenter> implements NoteContract.View {

    @BindView(R.id.note_list)
    RecyclerView mNoteList;
    @Inject
    NoteAdapter mAdapter;
    @BindView(R.id.note_back)
    AppCompatTextView mBack;
    @BindView(R.id.note_edit)
    AppCompatTextView mEdit;
    @BindView(R.id.note_toolbar)
    Toolbar mToolbar;
    private NoteEditDialog mNoteEditDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mNoteList.setLayoutManager(new LinearLayoutManager(this));
        mNoteList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.getNoteList();
    }

    @Override
    protected void initListener() {
        mBack.setOnClickListener(v -> finish());
        mEdit.setOnClickListener(v -> NoteEditActivity.start(this));
        mAdapter.setOnMenuClickListener(item -> {
            if (mNoteEditDialog == null) {
                mNoteEditDialog = NoteEditDialog.get();
            }
            mNoteEditDialog.setOnItemClickListener(new NoteEditDialog.OnItemClickListener() {
                @Override
                public void onEdit() {
                    Toast.makeText(NoteActivity.this, "编辑", Toast.LENGTH_SHORT).show();
                    mNoteEditDialog.dismiss();
                }

                @Override
                public void onDelete() {
                    Toast.makeText(NoteActivity.this, "删除", Toast.LENGTH_SHORT).show();
                    mNoteEditDialog.dismiss();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(NoteActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    mNoteEditDialog.dismiss();
                }
            });
            mNoteEditDialog.show(this);
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_note;
    }

    @Override
    public void showNote(List<NoteEntity> notes) {
        mAdapter.appendNote(notes);
    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NoteEditActivity.REQUEST_CODE == requestCode && NoteEditActivity.RESULT_CODE == resultCode && data != null) {
            NoteEntity noteEntity = data.getParcelableExtra(ActivityConstant.DATA);
            if (noteEntity != null) {
                mAdapter.appendNote(noteEntity);
            }
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
