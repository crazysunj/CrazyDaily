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
package com.crazysunj.domain.interactor.note;

import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.note.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author: sunjian
 * created on: 2018/10/3 下午6:01
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteDeleteNoteUseCase extends UseCase<Boolean, NoteDeleteNoteUseCase.Params> {

    private final NoteRepository mNoteRepository;

    @Inject
    public NoteDeleteNoteUseCase(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }

    @Override
    protected Flowable<Boolean> buildUseCaseObservable(Params params) {
        return mNoteRepository.deleteNote(params.id);
    }

    public static final class Params {
        private Long id;

        private Params(Long id) {
            this.id = id;
        }

        public static Params get(Long id) {
            return new Params(id);
        }
    }
}
