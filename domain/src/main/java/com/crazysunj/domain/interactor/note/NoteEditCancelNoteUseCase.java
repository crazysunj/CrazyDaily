package com.crazysunj.domain.interactor.note;

import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.note.NoteEditRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * author: sunjian
 * created on: 2018/10/3 下午6:01
 * description:
 */
public class NoteEditCancelNoteUseCase extends UseCase<Boolean, NoteEditCancelNoteUseCase.Params> {

    private final NoteEditRepository mNoteEditRepository;

    @Inject
    public NoteEditCancelNoteUseCase(NoteEditRepository noteEditRepository) {
        mNoteEditRepository = noteEditRepository;
    }

    @Override
    protected Flowable<Boolean> buildUseCaseObservable(Params params) {
        return mNoteEditRepository.cancelNote(params.id);
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
