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
 * @author: sunjian
 * created on: 2018/9/28 上午11:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
public class NoteEditPresenter extends BasePresenter<NoteEditContract.View> implements NoteEditContract.Presenter {

    private final NoteSaveNoteUseCase mNoteSaveNoteUseCase;
    private final NoteGetNoteUseCase mNoteGetNoteUseCase;
    private final NoteDeleteNoteUseCase mNoteDeleteNoteUseCase;

    @Inject
    NoteEditPresenter(NoteSaveNoteUseCase noteSaveNoteUseCase, NoteGetNoteUseCase noteGetNoteUseCase, NoteDeleteNoteUseCase noteDeleteNoteUseCase) {
        mNoteSaveNoteUseCase = noteSaveNoteUseCase;
        mNoteGetNoteUseCase = noteGetNoteUseCase;
        mNoteDeleteNoteUseCase = noteDeleteNoteUseCase;
    }

    @Override
    public void cancelNote(Long id) {
        mNoteDeleteNoteUseCase.execute(NoteDeleteNoteUseCase.Params.get(id), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean && mView != null) {
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
                if (aBoolean && mView != null) {
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
                if (mView != null) {
                    mView.showNote(noteEntity);
                }
            }
        });
    }

    @Override
    public void submitNote(NoteEntity noteEntity) {
        mNoteSaveNoteUseCase.execute(NoteSaveNoteUseCase.Params.get(noteEntity), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean && mView != null) {
                    mView.submitSuccess(noteEntity);
                }
            }
        });
    }
}
