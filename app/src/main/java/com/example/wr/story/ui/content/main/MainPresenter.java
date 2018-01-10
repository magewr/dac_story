package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryListByString;
import com.example.wr.story.interactor.GetStoryList;
import com.example.wr.story.interactor.RemoveStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.util.StoryItemUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR.
 */

public class MainPresenter extends Presenter<MainContract.View> implements MainContract.Presenter {

    GetStoryList getStoryList;
    RemoveStory removeStory;
    GetStoryListByString getStoryListByString;

    @Inject
    public MainPresenter(GetStoryList getStoryListUseCase, RemoveStory removeStory, GetStoryListByString getStoryListByString){
        this.getStoryList = getStoryListUseCase;
        this.removeStory = removeStory;
        this.getStoryListByString = getStoryListByString;
    }

    @Override
    public void onStartPresenter() {
        super.onStartPresenter();
        getStoryList();
    }

    @Override
    public void dispose() {
        getStoryList.dispose();
    }


    @Override
    public void onStoryItemSelected(int position) {
        int id = getView().getRecyclerViewAdapter().getData().get(position).t.getId();
        getView().showDetailActivityByStoryId(id);
    }

    @Override
    public void getStoryList() {
        getStoryList.execute(new GetStoryListObserver(), new Boolean(false));
    }

    @Override
    public void getSampleStoryList() {
        getStoryList.execute(new GetStoryListObserver(), new Boolean(true));
    }

    @Override
    public void removeStoryItem(int position, PresenterResultListener.OnSuccessListener successListener, PresenterResultListener.OnErrorListener errorListener) {
        StoryDTO targetItem = getView().getRecyclerViewAdapter().getData().get(position).t;
        removeStory.execute(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                successListener.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                errorListener.onError(e.getMessage());
            }
        }, targetItem);
    }

    @Override
    public void searchStory(String string) {
        getStoryListByString.execute(new DisposableSingleObserver<List<StoryDTO>>() {
            @Override
            public void onSuccess(List<StoryDTO> dtoList) {
                getView().getRecyclerViewAdapter().setNewData(StoryItemUtil.createSectionFromStory(dtoList));
            }

            @Override
            public void onError(Throwable e) {

            }
        }, string);
    }

    private final class GetStoryListObserver extends DisposableObserver<List<StoryDTO>> {

        @Override
        public void onNext(List<StoryDTO> storyDTOS) {
            getView().getRecyclerViewAdapter().getData().clear();
            getView().getRecyclerViewAdapter().addData(StoryItemUtil.createSectionFromStory(storyDTOS));
            getView().onRecyclerViewAdapterUpdated();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }


}
