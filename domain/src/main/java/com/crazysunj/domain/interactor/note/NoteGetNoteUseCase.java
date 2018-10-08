package com.crazysunj.domain.interactor.note;

import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.note.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:42
 * description:
 */
public class NoteGetNoteUseCase extends UseCase<NoteEntity, Void> {
    private final NoteRepository mNoteRepository;

    @Inject
    public NoteGetNoteUseCase(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }

    @Override
    protected Flowable<NoteEntity> buildUseCaseObservable(Void aVoid) {
        return mNoteRepository.getNote();
    }
}
