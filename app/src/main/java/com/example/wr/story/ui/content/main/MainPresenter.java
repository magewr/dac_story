package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryListByString;
import com.example.wr.story.interactor.GetStoryList;
import com.example.wr.story.interactor.RemoveStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.listener.SimpleDisposableCompletableObserver;
import com.example.wr.story.ui.listener.SimpleDisposableObserver;
import com.example.wr.story.ui.listener.SimpleDisposableSingleObserver;
import com.example.wr.story.ui.util.StoryItemUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR.
 */

public class MainPresenter extends Presenter<MainContract.View> implements MainContract.Presenter {

    GetStoryList getStoryList;
    RemoveStory removeStory;
    GetStoryListByString getStoryListByString;
    StorySectionAdapterModel adapterModel;

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
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        getStoryList.dispose();
        removeStory.dispose();
        getStoryListByString.dispose();
    }

    @Override
    public void setAdapterModel(StorySectionAdapterModel adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void onStoryItemSelected(int position) {
        long id = adapterModel.getStoryItem(position).getId();
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
    public void removeStoryItem(int position, PresenterResultListener listener) {
        StoryDTO targetItem = adapterModel.getStoryItem(position);
        removeStory.execute(new SimpleDisposableCompletableObserver(listener), targetItem);
    }

    @Override
    public void searchStory(String string) {
        getStoryListByString.execute(new SimpleDisposableSingleObserver<List<StoryDTO>>() {
            @Override
            public void onSuccess(List<StoryDTO> dtoList) {
                adapterModel.setNewData(StoryItemUtil.createSectionFromStory(dtoList), true);
            }
        }, string);
    }

    class GetStoryListObserver extends SimpleDisposableObserver<List<StoryDTO>> {
        @Override
        public void onNext(List<StoryDTO> storyDTOS) {
            adapterModel.setNewData(StoryItemUtil.createSectionFromStory(storyDTOS), false);
            getView().onRecyclerViewAdapterUpdated();
        }
    }


}
