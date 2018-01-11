package com.example.wr.story.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wr.story.App;
import com.example.wr.story.di.component.ActivityComponent;
import com.example.wr.story.di.component.ApplicationComponent;

import butterknife.ButterKnife;

/**
 * Created by WR.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    /**
     * Activity에서 사용할 Layout Resource를 리턴하는 메소드
     * @return R.layout.id
     */
    protected abstract int getLayoutId();

    /**
     * DI를 초기화하는 메소드. 일반적으로 여기서 component에 inject 함
     */
    protected abstract void initDagger();

    /**
     * Presenter를 초기화하는 메소드
     */
    protected abstract void initPresenter();

    protected Presenter presenter;
    protected ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        initDagger();
        initPresenter();

        if (presenter != null)
            presenter.onCreatePresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.onStartPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.onStopPresenter();
    }

    @Override
    protected void onDestroy() {
//        if (presenter != null)
//            presenter.onDestroyPresenter();
        super.onDestroy();
    }

    protected ApplicationComponent getApplicationComponent() {
        return App.get(this).getApplicationComponent();
    }
}
