package com.example.wr.story.ui.content.splash;

import com.example.wr.story.ui.base.Presenter;

import javax.inject.Inject;

/**
 * Created by WR.
 */

public class SplashPresenter extends Presenter<SplashContract.View> implements SplashContract.Presenter {

    @Inject
    public SplashPresenter() {
    }

    @Override
    public void onCreatePresenter() {
        super.onCreatePresenter();
        getView().loadSplashImage();
        getView().moveToMainActivity();
    }
}
