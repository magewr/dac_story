package com.example.wr.story.ui.content.camera;

import com.example.wr.story.interactor.SavePicture;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR on 2018-01-10.
 */

public class CameraPresenter extends Presenter<CameraContract.View> implements CameraContract.Presenter {

    SavePicture savePicture;

    @Inject
    CameraPresenter(SavePicture savePicture){
        this.savePicture = savePicture;
    }

    @Override
    public void savePicture(String imagePath, byte[] data, PresenterResultListener.OnSuccessListener successListener, PresenterResultListener.OnErrorListener errorListener) {
        savePicture.execute(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String imagePath) {
                getView().onSavePicture(imagePath);
                successListener.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                errorListener.onError(e.getMessage());
            }
        }, SavePicture.Params.makeParams(imagePath, data));
    }
}
