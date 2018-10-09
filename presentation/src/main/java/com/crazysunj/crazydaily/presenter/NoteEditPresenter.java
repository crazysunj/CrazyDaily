package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.note.NoteDeleteNoteUseCase;
import com.crazysunj.domain.interactor.note.NoteGetNoteUseCase;
import com.crazysunj.domain.interactor.note.NoteSaveNoteUseCase;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2018/9/28 上午11:05
 * description:
 */
@ActivityScope
public class NoteEditPresenter extends BasePresenter<NoteEditContract.View> implements NoteEditContract.Presenter {

    private final NoteSaveNoteUseCase mNoteSaveNoteUseCase;
    private final NoteGetNoteUseCase mNoteGetNoteUseCase;
    private final NoteDeleteNoteUseCase mNoteDeleteNoteUseCase;

    @Inject
    public NoteEditPresenter(NoteSaveNoteUseCase noteSaveNoteUseCase, NoteGetNoteUseCase noteGetNoteUseCase, NoteDeleteNoteUseCase noteDeleteNoteUseCase) {
        mNoteSaveNoteUseCase = noteSaveNoteUseCase;
        mNoteGetNoteUseCase = noteGetNoteUseCase;
        mNoteDeleteNoteUseCase = noteDeleteNoteUseCase;
    }

    @Override
    public void cancelNote(Long id) {
        mNoteDeleteNoteUseCase.execute(NoteDeleteNoteUseCase.Params.get(id), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mView.cancelSuccess();
                }
            }
        });
    }

    @Override
    public void saveNote(NoteEntity noteEntity) {
        mNoteSaveNoteUseCase.execute(NoteSaveNoteUseCase.Params.get(noteEntity), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mView.saveSuccess();
                }
            }
        });
    }

    @Override
    public void getNote() {
        mNoteGetNoteUseCase.execute(new BaseSubscriber<NoteEntity>() {
            @Override
            public void onNext(NoteEntity noteEntity) {
                mView.showNote(noteEntity);
            }
        });
    }

    @Override
    public void submitNote(NoteEntity noteEntity) {
        mNoteSaveNoteUseCase.execute(NoteSaveNoteUseCase.Params.get(noteEntity), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mView.submitSuccess(noteEntity);
                }
            }
        });
    }
}
