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
package com.crazysunj.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author: sunjian
 * created on: 2017/9/5 下午5:34
 * description: https://github.com/crazysunj/CrazyDaily
 */
public abstract class UseCase<T, Params> {

    private final CompositeDisposable mDisposables;

    public UseCase() {
        this.mDisposables = new CompositeDisposable();
    }

    protected abstract Flowable<T> buildUseCaseObservable(Params params);

    public void execute(Params params, DisposableSubscriber<T> subscriber) {
        Preconditions.checkNotNull(subscriber);
        final Flowable<T> flowable = this.buildUseCaseObservable(params);
        addDisposable(flowable.subscribeWith(subscriber));
    }

    public <P> void execute(Flowable<P> flowable, DisposableSubscriber<P> subscriber) {
        Preconditions.checkNotNull(flowable);
        Preconditions.checkNotNull(subscriber);
        addDisposable(flowable.subscribeWith(subscriber));
    }

    public void execute(DisposableSubscriber<T> subscriber) {
        execute((Params) null, subscriber);
    }

    public void dispose() {
        if (mDisposables.isDisposed()) {
            return;
        }
        mDisposables.dispose();
    }

    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(mDisposables);
        mDisposables.add(disposable);
    }
}
