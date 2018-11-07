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
package com.crazysunj.crazydaily.presenter.contract;

import com.crazysunj.crazydaily.base.IPresenter;
import com.crazysunj.crazydaily.base.IView;
import com.crazysunj.domain.entity.note.NoteEntity;

/**
 * @author: sunjian
 * created on: 2018/9/27 下午3:57
 * description: https://github.com/crazysunj/CrazyDaily
 */
public interface NoteEditContract {
    interface View extends IView {
        /**
         * 展示草稿
         */
        void showNote(NoteEntity noteEntity);

        void cancelSuccess();

        void saveSuccess();

        void submitSuccess(NoteEntity noteEntity);
    }

    interface Presenter extends IPresenter<View> {
        /**
         * 取消草稿
         */
        void cancelNote(Long id);

        /**
         * 保存草稿
         */
        void saveNote(NoteEntity noteEntity);

        /**
         * 获取草稿
         */
        void getNote();

        /**
         * 上传笔记
         */
        void submitNote(NoteEntity noteEntity);
    }
}
