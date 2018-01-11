package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryById;
import com.example.wr.story.interactor.UpdateStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.exception.NoPictureException;
import com.example.wr.story.ui.exception.StoryNotFoundException;
import com.example.wr.story.ui.listener.PresenterResultListener;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR.
 */

public class DetailPresenter extends Presenter<DetailContract.View> implements DetailContract.Presenter  {

    //UseCase
    private GetStoryById getStoryById;
    private UpdateStory updateStory;
    //Repository에서 받은 원본 Story Item
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
    public void setStoryById(long storyId, PresenterResultListener listener) {
        getStoryById.execute(new DisposableSingleObserver<StoryDTO>() {
            @Override
            public void onSuccess(StoryDTO storyDTO) {
                //Story를 못찾을 경우 에러처리
                if (storyDTO == null) {
                    listener.onResult(false, new StoryNotFoundException().getMessage());
                    return;
                }
                detailStoryItem = storyDTO;
                getView().onGetStory();
                listener.onResult(true, null);
            }

            @Override
            public void onError(Throwable e) {
                listener.onResult(false, e.getMessage());
            }
        }, storyId);
    }

    @Override
    public void updateStory(StoryDTO item, PresenterResultListener listener) {
        // 사진이 하나도 없을경우 저장불가 - 에러처리
        if (item.getImagePathList().size() == 0) {
            listener.onResult(false, new NoPictureException().getMessage());
            return;
        }
        updateStory.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                detailStoryItem = item;
                listener.onResult(true, null);
            }

            @Override
            public void onError(Throwable e) {
                listener.onResult(true, e.getMessage());
            }
        }, item);
    }

    @Override
    public void onPictureAdded(List<String> imagePath) {
        getView().getThumbnailAdapter().addImagePathList(imagePath);
        getView().getThumbnailAdapter().notifyDataSetChanged();
    }

    @Override
    public StoryDTO copyDetailStoryItem() {
        return new StoryDTO(detailStoryItem);
    }
}
