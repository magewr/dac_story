package com.example.wr.story.ui.content.add;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.AddStoryCU;
import com.example.wr.story.ui.base.BasePresenter;
import com.example.wr.story.ui.exception.NoPictureException;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * Created by WR.
 */

public class AddPresenter extends BasePresenter<AddContract.View> implements AddContract.Presenter  {

    private AddStoryCU addStory;

    @Inject
    AddPresenter(AddStoryCU addStory){
        this.addStory = addStory;
    }

    @Override
    public void onCreatePresenter() {
        super.onCreatePresenter();
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        addStory.dispose();
    }

    @Override
    public void onStoryItemModified(StoryDTO item, PresenterResultListener listener) {
        if (item.getImagePathList().size() == 0) {
            listener.onResult(false, new NoPictureException().getMessage());
            return;
        }
        addStory.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                listener.onResult(true, null);
            }

            @Override
            public void onError(Throwable e) {
                listener.onResult(false, e.getMessage());
            }
        }, item);
    }

}
