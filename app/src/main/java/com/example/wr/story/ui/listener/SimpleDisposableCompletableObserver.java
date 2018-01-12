package com.example.wr.story.ui.listener;

import android.widget.Toast;

import com.example.wr.story.App;
import com.example.wr.story.R;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * Created by WR on 2018-01-12.
 * CompletableObserver의 결과값을 편하게 처리하기 위한 클래스로 기본값에 대한 처리가 되어있다.
 * 결과에 따라 필요한 로직이 있다면 오버라이드 하여 사용하면 된다.
 * PresenterResultListener가 제공받았을 경우 자동으로 이벤트 결과를 전달해준다.
 */

public class SimpleDisposableCompletableObserver extends DisposableCompletableObserver {

    PresenterResultListener presenterResultListener;

    public SimpleDisposableCompletableObserver() {
        super();
    }

    public SimpleDisposableCompletableObserver(PresenterResultListener listener) {
        super();
        presenterResultListener = listener;
    }

    @Override
    public void onComplete() {
        if (presenterResultListener != null)
            presenterResultListener.onResult(true, null);
    }

    @Override
    public void onError(Throwable e) {
        if (presenterResultListener != null)
            presenterResultListener.onResult(false, e.getMessage());
        else
            Toast.makeText(App.getContext(), App.getContext().getString(R.string.error), Toast.LENGTH_LONG).show();
    }
}
