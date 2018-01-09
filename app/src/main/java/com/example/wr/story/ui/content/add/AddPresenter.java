package com.example.wr.story.ui.content.add;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.AddStory;
import com.example.wr.story.interactor.GetStoryById;
import com.example.wr.story.interactor.UpdateStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.content.detail.DetailContract;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

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
    public void onStoryItemModified(StoryDTO item, PresenterResultListener.OnSuccessListener onSuccessListener, PresenterResultListener.OnErrorListener onErrorListener) {
        addStory.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                onSuccessListener.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                onErrorListener.onError(e.getMessage());
            }
        }, item);
    }

}
