package com.crazysunj.crazydaily.ui.note;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crazysunj.crazydaily.R;
import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.NotePresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteContract;
import com.crazysunj.domain.entity.note.NoteEntity;

import java.util.List;

import butterknife.BindView;

/**
 * author: sunjian
 * created on: 2018/10/8 下午4:25
 * description:
 */
public class NoteActivity extends BaseActivity<NotePresenter> implements NoteContract.View {

    @BindView(R.id.note_list)
    RecyclerView mNoteList;

    @Override
    protected void initView() {
        setTitle("笔记");
        showBack();
        mNoteList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_note;
    }

    @Override
    public void showNote(List<NoteEntity> noteEntity) {

    }

    @Override
    public void deleteSuccess() {

    }
}
