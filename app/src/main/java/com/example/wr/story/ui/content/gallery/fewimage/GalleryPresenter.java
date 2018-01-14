package com.example.wr.story.ui.content.gallery.fewimage;

import android.graphics.Bitmap;

import com.example.wr.story.ui.base.BasePresenter;
import com.example.wr.story.ui.listener.SimpleDisposableObserver;
import com.example.wr.story.ui.util.StoryItemUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WR on 2018-01-14.
 */

public class GalleryPresenter extends BasePresenter<GalleryContract.View> implements GalleryContract.Presenter {

    @Inject
    GalleryPresenter() {}

    @Override
    public void loadBitmapFromImagePathList(List<String> list) {
        StoryItemUtil.getBitmapObservableFromImagePathList(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleDisposableObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        getView().onBitmapReady(bitmap);
                    }

                    @Override
                    public void onComplete() {
                        getView().moveToClickedImage();
                    }
                });
    }
}
