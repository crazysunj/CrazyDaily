package com.crazysunj.crazydaily.ui.note;

import com.crazysunj.crazydaily.base.BaseActivity;
import com.crazysunj.crazydaily.presenter.NoteEditPresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.domain.entity.note.NoteEntity;

/**
 * author: sunjian
 * created on: 2018/9/27 下午3:56
 * description:
 */
public class NoteEditActivity extends BaseActivity<NoteEditPresenter> implements NoteEditContract.View {

    @Override
    protected int getContentResId() {
        return 0;
    }

    @Override
    public void showTemNote(NoteEntity noteEntity) {

    }
}
