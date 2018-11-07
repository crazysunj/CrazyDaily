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
package com.crazysunj.domain.interactor.download;

import com.crazysunj.domain.interactor.UseCase;
import com.crazysunj.domain.repository.download.DownloadRepository;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class DownloadUseCase extends UseCase<File, DownloadUseCase.Params> {

    private final DownloadRepository mDownloadRepository;

    @Inject
    public DownloadUseCase(DownloadRepository downloadRepository) {
        mDownloadRepository = downloadRepository;
    }

    @Override
    protected Flowable<File> buildUseCaseObservable(Params params) {
        return mDownloadRepository.download(params.taskId, params.url, params.saveFileDir);
    }


    public static final class Params {
        private final int taskId;
        private final String url;
        private final File saveFileDir;

        private Params(int taskId, String url, File saveFileDir) {
            this.taskId = taskId;
            this.url = url;
            this.saveFileDir = saveFileDir;
        }

        public static Params get(int taskId, String url, File saveFileDir) {
            return new Params(taskId, url, saveFileDir);
        }
    }
}
