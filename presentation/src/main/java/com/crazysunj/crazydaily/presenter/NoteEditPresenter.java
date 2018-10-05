package com.crazysunj.crazydaily.presenter;

import com.crazysunj.crazydaily.base.BasePresenter;
import com.crazysunj.crazydaily.base.BaseSubscriber;
import com.crazysunj.crazydaily.di.scope.ActivityScope;
import com.crazysunj.crazydaily.presenter.contract.NoteEditContract;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.note.NoteEditCancelNoteUseCase;
import com.crazysunj.domain.interactor.note.NoteEditGetNoteUseCase;
import com.crazysunj.domain.interactor.note.NoteEditSaveNoteUseCase;

import javax.inject.Inject;

/**
 * author: sunjian
 * created on: 2018/9/28 上午11:05
 * description:
 */
@ActivityScope
public class NoteEditPresenter extends BasePresenter<NoteEditContract.View> implements NoteEditContract.Presenter {

    private final NoteEditSaveNoteUseCase mNoteEditSaveNoteUseCase;
    private final NoteEditGetNoteUseCase mNoteEditGetNoteUseCase;
    private final NoteEditCancelNoteUseCase mNoteEditCancelNoteUseCase;

    @Inject
    public NoteEditPresenter(NoteEditSaveNoteUseCase noteEditSaveNoteUseCase, NoteEditGetNoteUseCase noteEditGetNoteUseCase, NoteEditCancelNoteUseCase noteEditCancelNoteUseCase) {
        mNoteEditSaveNoteUseCase = noteEditSaveNoteUseCase;
        mNoteEditGetNoteUseCase = noteEditGetNoteUseCase;
        mNoteEditCancelNoteUseCase = noteEditCancelNoteUseCase;
    }

    @Override
    public void cancelNote(Long id) {
        mNoteEditCancelNoteUseCase.execute(NoteEditCancelNoteUseCase.Params.get(id), new BaseSubscriber<Boolean>() {
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
        mNoteEditSaveNoteUseCase.execute(NoteEditSaveNoteUseCase.Params.get(noteEntity), new BaseSubscriber<Boolean>() {
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
        mNoteEditGetNoteUseCase.execute(new BaseSubscriber<NoteEntity>() {
            @Override
            public void onNext(NoteEntity noteEntity) {
                mView.showNote(noteEntity);
            }
        });
    }

    @Override
    public void submitNote(NoteEntity noteEntity) {
        mNoteEditSaveNoteUseCase.execute(NoteEditSaveNoteUseCase.Params.get(noteEntity), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    mView.submitSuccess(noteEntity);
                }
            }
        });
    }
}
