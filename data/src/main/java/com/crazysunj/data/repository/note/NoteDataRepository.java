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
package com.crazysunj.data.repository.note;

import com.crazysunj.data.api.DBHelper;
import com.crazysunj.data.util.RxTransformerUtil;
import com.crazysunj.domain.db.NoteEntityDao;
import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.repository.note.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: sunjian
 * created on: 2018/10/3 下午6:11
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteDataRepository implements NoteRepository {

    private final NoteEntityDao mNoteDao;

    @Inject
    public NoteDataRepository(DBHelper dbHelper) {
        mNoteDao = dbHelper.getNoteDao();
    }

    @Override
    public Flowable<Boolean> deleteNote(Long id) {
        return Flowable.just(id)
                .observeOn(Schedulers.io())
                .map(this::deleteNoteByDB)
                .compose(RxTransformerUtil.normalTransformer());
    }

    private boolean deleteNoteByDB(Long id) {
        mNoteDao.deleteByKey(id);
        return true;
    }

    @Override
    public Flowable<Boolean> saveNote(NoteEntity noteEntity) {
        return Flowable.just(noteEntity)
                .observeOn(Schedulers.io())
                .map(this::saveNoteByDB)
                .compose(RxTransformerUtil.normalTransformer());
    }

    private boolean saveNoteByDB(NoteEntity note) {
        long insertOrReplace = mNoteDao.insertOrReplace(note);
        return insertOrReplace != -1;
    }

    @Override
    public Flowable<NoteEntity> getNote() {
        return Flowable.just(NoteEntity.FLAG_DRAFT)
                .observeOn(Schedulers.io())
                .flatMap(this::getNoteByDB)
                .compose(RxTransformerUtil.normalTransformer());
    }

    @Nullable
    private Flowable<NoteEntity> getNoteByDB(Integer flag) {
        List<NoteEntity> notes = mNoteDao.queryBuilder().where(NoteEntityDao.Properties.Flag.eq(flag)).orderDesc(NoteEntityDao.Properties.Id).list();
        if (notes == null || notes.isEmpty()) {
            return Flowable.empty();
        }
        return Flowable.just(notes.get(0));
    }

    @Override
    public Flowable<List<NoteEntity>> getNoteList() {
        return Flowable.just(NoteEntity.FLAG_SUBMIT)
                .observeOn(Schedulers.io())
                .flatMap(this::getNoteListByDB)
                .compose(RxTransformerUtil.normalTransformer());
    }

    @Nullable
    private Flowable<List<NoteEntity>> getNoteListByDB(Integer flag) {
        List<NoteEntity> notes = mNoteDao.queryBuilder().where(NoteEntityDao.Properties.Flag.eq(flag)).orderDesc(NoteEntityDao.Properties.Id).list();
        return Flowable.just(notes);
    }
}
