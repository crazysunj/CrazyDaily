package com.crazysunj.domain.interactor.note;

import com.crazysunj.domain.entity.note.NoteEntity;
import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.note.NoteRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:01
 * description:
 */
public class NoteSaveNoteUseCase extends UseCase<Boolean, NoteSaveNoteUseCase.Params> {

    private final NoteRepository mNoteRepository;

    @Inject
    public NoteSaveNoteUseCase(NoteRepository noteRepository) {
        mNoteRepository = noteRepository;
    }

    @Override
    protected Flowable<Boolean> buildUseCaseObservable(Params params) {
        return mNoteRepository.saveNote(params.noteEntity);
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
