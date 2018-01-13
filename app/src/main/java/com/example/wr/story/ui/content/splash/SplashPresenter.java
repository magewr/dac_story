package com.example.wr.story.ui.content.splash;

import com.example.wr.story.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by WR.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    @Inject
    public SplashPresenter() {
    }

    @Override
    public void onCreatePresenter() {
        super.onCreatePresenter();
        getView().loadSplashImage();
    }

}
