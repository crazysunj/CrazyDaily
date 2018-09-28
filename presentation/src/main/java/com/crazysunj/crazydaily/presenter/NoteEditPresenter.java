package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.domain.entity.note.NoteEntity;

/**
 * author: sunjian
 * created on: 2018/9/28 上午11:05
 * description:
 */
public class NoteEditPresenter extends BasePresenter<NoteEditContract.View> implements NoteEditContract.Presenter {
    
    @Override
    public void saveTemNote(NoteEntity noteEntity) {

    }

    @Override
    public void getTemNote() {

    }

    @Override
    public void submitNote(NoteEntity noteEntity) {

    }
}
