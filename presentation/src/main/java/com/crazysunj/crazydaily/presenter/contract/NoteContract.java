package com.crazysunj.crazydaily.presenter.contract;

import com.crazysunj.crazydaily.base.IPresenter;
import com.crazysunj.crazydaily.base.IView;
import com.crazysunj.domain.entity.note.NoteEntity;

import java.util.List;

/**
 * author: sunjian
 * created on: 2018/10/8 下午4:29
 * description:
 */
public interface NoteContract {
    interface View extends IView {
        /**
         * 展示笔记
         */
        void showNote(List<NoteEntity> notes);

        void deleteSuccess();
    }

    interface Presenter extends IPresenter<View> {
        /**
         * 删除笔记
         */
        void deleteNote(Long id);

        /**
         * 获取笔记列表
         */
        void getNoteList();
    }
}
