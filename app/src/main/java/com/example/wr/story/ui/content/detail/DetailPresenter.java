package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryById;
import com.example.wr.story.interactor.UpdateStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by WR.
 */

public class DetailPresenter extends Presenter<DetailContract.View> implements DetailContract.Presenter  {

    GetStoryById getStoryById;
    UpdateStory updateStory;

    @Inject
    DetailPresenter(GetStoryById getStoryById, UpdateStory updateStory){
        this.getStoryById = getStoryById;
        this.updateStory = updateStory;
    }

    @Override
    public void onCreatePresenter() {
        super.onCreatePresenter();
    }

    @Override
    public void setStoryById(int storyId) {
        getStoryById.execute(new DisposableObserver<StoryDTO>() {
            @Override
            public void onNext(StoryDTO storyDTO) {
                getView().onGetStory(storyDTO);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, storyId);
    }

    @Override
    public void onStoryItemModified(StoryDTO item, PresenterResultListener listener) {
        updateStory.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                listener.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e.getMessage());
            }
        }, item);
    }
}
