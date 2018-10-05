package com.crazysunj.domain.repository.note;

import com.crazysunj.domain.entity.note.NoteEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:05
 * description:
 */
public interface NoteEditRepository {
    Flowable<Boolean> cancelNote(Long id);

    Flowable<Boolean> saveNote(NoteEntity noteEntity);

    Flowable<NoteEntity> getNote();

    Flowable<List<NoteEntity>> getNoteList();
}
