package com.example.wr.story.interactor.base;

import com.example.wr.story.data.DataRepository;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WR.
 */

public abstract class SingleUseCase<T, Param> {
    private final CompositeDisposable disposables;
    protected DataRepository dataRepository;

    protected SingleUseCase(DataRepository dataRepository) {
        this.disposables = new CompositeDisposable();
        this.dataRepository = dataRepository;
    }

    protected abstract Single<T> buildUseCaseSingle(Param param);

    public void execute(DisposableSingleObserver<T> observer, Param param) {
        if (observer != null) {
            final Single<T> single = this.buildUseCaseSingle(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            addDisposable(single.subscribeWith(observer));
        }
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
