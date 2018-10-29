package com.crazysunj.domain.interactor.note;

import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.note.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:42
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class NoteGetListNoteUseCase extends UseCase<List<NoteEntity>, Void> {
    private final NoteRepository mNoteRepository;

    @Inject
    public NoteGetListNoteUseCase(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }

    @Override
    protected Flowable<List<NoteEntity>> buildUseCaseObservable(Void aVoid) {
        return mNoteRepository.getNoteList();
    }
}
