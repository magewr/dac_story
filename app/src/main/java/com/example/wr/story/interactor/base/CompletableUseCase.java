package com.example.wr.story.interactor.base;

import com.example.wr.story.data.DataRepository;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WR.
 */

public abstract class CompletableUseCase<Param> {
    private final CompositeDisposable disposables;
    protected DataRepository dataRepository;

    protected CompletableUseCase(DataRepository dataRepository) {
        this.disposables = new CompositeDisposable();
        this.dataRepository = dataRepository;
    }

    protected abstract Completable buildUseCaseCompletable(Param param);

    public void execute(DisposableCompletableObserver observer, Param param) {
            final Completable completable = this.buildUseCaseCompletable(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            addDisposable(completable.subscribeWith(observer));
    }

    public void dispose() {
        if (!disposables.isDisposed())
            disposables.dispose();
    }

    private void addDisposable(Disposable disposable) {
        if (disposable != null && this.disposables != null)
            disposables.add(disposable);
    }


}