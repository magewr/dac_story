package com.example.wr.story.ui.content.camera;

import com.example.wr.story.interactor.SavePictureSU;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR on 2018-01-10.
 */

public class CameraPresenter extends Presenter<CameraContract.View> implements CameraContract.Presenter {

    SavePictureSU savePicture;

    @Inject
    CameraPresenter(SavePictureSU savePicture){
        this.savePicture = savePicture;
    }

    @Override
    public void savePicture(String imagePath, byte[] data, PresenterResultListener listener) {
        savePicture.execute(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String imagePath) {
                getView().onSavePicture(imagePath);
                listener.onResult(true, null);
            }

            @Override
            public void onError(Throwable e) {
                listener.onResult(false, e.getMessage());
            }
        }, SavePictureSU.Params.makeParams(imagePath, data));
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        savePicture.dispose();
    }
}
