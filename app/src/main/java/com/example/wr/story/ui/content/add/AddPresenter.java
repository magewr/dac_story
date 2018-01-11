package com.example.wr.story.ui.content.add;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.AddStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.exception.NoPictureException;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * Created by WR.
 */

public class AddPresenter extends Presenter<AddContract.View> implements AddContract.Presenter  {

    private AddStory addStory;

    @Inject
    AddPresenter(AddStory addStory){
        this.addStory = addStory;
    }

    @Override
    public void onCreatePresenter() {
        super.onCreatePresenter();
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
