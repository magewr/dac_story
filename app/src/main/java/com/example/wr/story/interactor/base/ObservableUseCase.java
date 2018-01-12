package com.example.wr.story.interactor.base;

import com.example.wr.story.data.DataRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WR.
 * UseCase 중 Observable 를 사용하는 경우의 추상 클래스
 * Observable 이므로 결과는 Next, Complete, Error 가 존재한다.
 */

public abstract class ObservableUseCase<T, Param> {
    private final CompositeDisposable disposables;
    protected DataRepository dataRepository;

    protected ObservableUseCase(DataRepository dataRepository) {
        this.disposables = new CompositeDisposable();
        this.dataRepository = dataRepository;
    }

    protected abstract Observable<T> buildUseCaseObservable(Param param);

    public void execute(DisposableObserver<T> observer, Param param) {
        if (observer != null) {
            final Observable<T> observable = this.buildUseCaseObservable(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            addDisposable(observable.subscribeWith(observer));
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
