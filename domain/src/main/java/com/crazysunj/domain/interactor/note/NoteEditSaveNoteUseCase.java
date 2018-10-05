package com.crazysunj.domain.interactor.note;

import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.note.NoteEditRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:01
 * description:
 */
public class NoteEditSaveNoteUseCase extends UseCase<Boolean, NoteEditSaveNoteUseCase.Params> {

    private final NoteEditRepository mNoteEditRepository;

    @Inject
    public NoteEditSaveNoteUseCase(NoteEditRepository noteEditRepository) {
        mNoteEditRepository = noteEditRepository;
    }

    @Override
    protected Flowable<Boolean> buildUseCaseObservable(Params params) {
        return mNoteEditRepository.saveNote(params.noteEntity);
    }

    public static final class Params {
        private NoteEntity noteEntity;

        private Params(NoteEntity noteEntity) {
            this.noteEntity = noteEntity;
        }

        public static Params get(NoteEntity noteEntity) {
            return new Params(noteEntity);
        }
    }
}
