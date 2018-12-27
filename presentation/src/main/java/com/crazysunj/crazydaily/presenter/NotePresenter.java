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
import com.crazysunj.crazydaily.presenter.contract.NoteContract;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.note.NoteDeleteNoteUseCase;
import com.crazysunj.domain.interactor.note.NoteGetListNoteUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * @author: sunjian
 * created on: 2018/10/8 下午4:31
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NotePresenter extends BasePresenter<NoteContract.View> implements NoteContract.Presenter {

    private final NoteGetListNoteUseCase mNoteGetListNoteUseCase;
    private final NoteDeleteNoteUseCase mNoteDeleteNoteUseCase;

    @Inject
    NotePresenter(NoteGetListNoteUseCase noteGetListNoteUseCase, NoteDeleteNoteUseCase noteDeleteNoteUseCase) {
        mNoteGetListNoteUseCase = noteGetListNoteUseCase;
        mNoteDeleteNoteUseCase = noteDeleteNoteUseCase;
    }

    @Override
    public void deleteNote(Long id) {
        mNoteDeleteNoteUseCase.execute(NoteDeleteNoteUseCase.Params.get(id), new BaseSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean && mView != null) {
                    mView.deleteSuccess(id);
                }
            }
        });
    }

    @Override
    public void getNoteList() {
        mNoteGetListNoteUseCase.execute(new BaseSubscriber<List<NoteEntity>>() {
            @Override
            public void onNext(List<NoteEntity> noteEntities) {
                if (mView != null) {
                    mView.showNote(noteEntities);
                }
            }
        });
    }
}
