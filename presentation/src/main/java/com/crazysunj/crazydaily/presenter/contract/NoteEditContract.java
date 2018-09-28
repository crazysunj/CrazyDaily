package com.crazysunj.crazydaily.presenter.contract;

import com.crazysunj.crazydaily.base.IPresenter;
import com.crazysunj.crazydaily.base.IView;
import com.crazysunj.domain.entity.note.NoteEntity;

/**
 * author: sunjian
 * created on: 2018/9/27 下午3:57
 * description:
 */
public interface NoteEditContract {
    interface View extends IView {
        /**
         * 展示草稿
         */
        void showTemNote(NoteEntity noteEntity);
    }

    interface Presenter extends IPresenter<View> {
        /**
         * 保存草稿
         */
        void saveTemNote(NoteEntity noteEntity);

        /**
         * 获取草稿
         */
        void getTemNote();

        /**
         * 上传笔记
         */
        void submitNote(NoteEntity noteEntity);
    }
}
