package com.example.wr.story.ui.content.detail.gallery;

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

public class GalleryPresenter extends Presenter<GalleryContract.View> implements GalleryContract.Presenter  {

    GetStoryById getStoryById;

    @Inject
    GalleryPresenter(GetStoryById getStoryById){
        this.getStoryById = getStoryById;
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
}
