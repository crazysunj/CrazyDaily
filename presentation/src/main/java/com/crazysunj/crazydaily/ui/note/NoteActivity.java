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

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.constant.ActivityConstant;
import com.crazysunj.crazydaily.presenter.NotePresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteContract;
import com.crazysunj.crazydaily.ui.adapter.NoteAdapter;
import com.crazysunj.crazydaily.ui.note.dialog.NoteEditDialog;
import com.crazysunj.crazydaily.view.dialog.CrazyDailyAlertDialog;
import com.crazysunj.domain.entity.note.NoteEntity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author: sunjian
 * created on: 2018/10/8 下午4:25
 * description: https://github.com/crazysunj/CrazyDaily
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
    private CrazyDailyAlertDialog mDeleteDialog;

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
                    NoteEditActivity.start(NoteActivity.this, item);
                    mNoteEditDialog.dismiss();
                }

                @Override
                public void onDelete() {
                    showDeleteDialog(item.getId());
                }

                @Override
                public void onCancel() {
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
    public void deleteSuccess(Long id) {
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        mAdapter.removeNote(id);
        if (mDeleteDialog != null) {
            mDeleteDialog.dismiss();
        }
        if (mNoteEditDialog != null) {
            mNoteEditDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NoteEditActivity.REQUEST_CODE == requestCode && NoteEditActivity.RESULT_CREATE_CODE == resultCode && data != null) {
            NoteEntity noteEntity = data.getParcelableExtra(ActivityConstant.DATA);
            if (noteEntity != null) {
                mAdapter.appendNote(noteEntity);
                RecyclerView.LayoutManager layoutManager = mNoteList.getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.scrollToPosition(0);
                }
            }
        } else if (NoteEditActivity.REQUEST_CODE == requestCode && NoteEditActivity.RESULT_EDIT_CODE == resultCode && data != null) {
            NoteEntity noteEntity = data.getParcelableExtra(ActivityConstant.DATA);
            if (noteEntity != null) {
                mAdapter.changeNote(noteEntity);
            }
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    private void showDeleteDialog(Long id) {
        if (mDeleteDialog == null) {
            mDeleteDialog = new CrazyDailyAlertDialog.Builder()
                    .setMessgae("确认删除这条笔记么？")
                    .setNegative("确定")
                    .setPositive("取消")
                    .setOnPositiveClickListener(v -> mDeleteDialog.dismiss())
                    .build();
        }
        mDeleteDialog.setOnNegativeClickListener(v -> mPresenter.deleteNote(id));
        mDeleteDialog.show(this);
    }
}
