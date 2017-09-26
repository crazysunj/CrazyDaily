package com.crazysunj.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

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

    public void execute(DisposableSubscriber<T> subscriber) {
        execute(null, subscriber);
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
