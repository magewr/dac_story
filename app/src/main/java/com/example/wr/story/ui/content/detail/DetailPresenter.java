package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryById;
import com.example.wr.story.interactor.UpdateStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.listener.PresenterResultListener;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR.
 */

public class DetailPresenter extends Presenter<DetailContract.View> implements DetailContract.Presenter  {

    private GetStoryById getStoryById;
    private UpdateStory updateStory;
    private StoryDTO detailStoryItem;

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
    public void setStoryById(long storyId) {
        getStoryById.execute(new DisposableSingleObserver<StoryDTO>() {
            @Override
            public void onSuccess(StoryDTO storyDTO) {
                detailStoryItem = storyDTO;
                getView().onGetStory();
            }

            @Override
            public void onError(Throwable e) {

            }
        }, storyId);
    }

    @Override
    public void onStoryItemModified(StoryDTO item, PresenterResultListener.OnSuccessListener onSuccessListener, PresenterResultListener.OnErrorListener onErrorListener) {
        if (item.getImagePathList().size() == 0) {
            onErrorListener.onError(new IllegalStateException("no pictures").getMessage());
            return;
        }
        updateStory.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                detailStoryItem = item;
                onSuccessListener.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                onErrorListener.onError(e.getMessage());
            }
        }, item);
    }

    @Override
    public StoryDTO copyDetailStoryItem() {
        return new StoryDTO(detailStoryItem);
    }
}
