package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryListByStringSU;
import com.example.wr.story.interactor.GetStoryListOU;
import com.example.wr.story.interactor.RemoveStoryCU;
import com.example.wr.story.ui.base.BasePresenter;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.listener.SimpleDisposableCompletableObserver;
import com.example.wr.story.ui.listener.SimpleDisposableObserver;
import com.example.wr.story.ui.listener.SimpleDisposableSingleObserver;
import com.example.wr.story.ui.util.StoryItemUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by WR.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    GetStoryListOU getStoryList;
    RemoveStoryCU removeStory;
    GetStoryListByStringSU getStoryListByString;
    StorySectionAdapterModel adapterModel;

    @Inject
    public MainPresenter(GetStoryListOU getStoryListUseCase, RemoveStoryCU removeStory, GetStoryListByStringSU getStoryListByString){
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
        if (adapterModel.getStoryItem(position) != null) {
            long id = adapterModel.getStoryItem(position).getId();
            getView().showDetailActivityByStoryId(id);
        }
    }

    @Override
    public void getStoryList() {
        getView().showRefresh(true);
        getStoryList.execute(new GetStoryListObserver(), new Boolean(false));
    }

    @Override
    public void getSampleStoryList() {
        getView().showRefresh(true);
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

    @Override
    public void handleOnBackPressed() {
        // 검색창이 포커스를 가지고 있을 경우 검색창의 포커스를 해제하고 이벤트 무시
        if (getView().setSearchFocusIfChangeable(false))
            return;
        // 검색창에 텍스트가 존재할경우(검색중) 텍스트를 제거한 뒤 이벤트 무시
        if (getView().hasSearchViewQueryString()) {
            getView().clearSearchVIewQueryString();
            return;
        }
        // 종료 가능한 상황일경우 종료 AlertDialog 출력
        getView().showFinishAlertDialog();
    }

    @Override
    public void handleOnSwipeRefresh() {
        getView().clearSearchVIewQueryString();
        getStoryList();
    }

    class GetStoryListObserver extends SimpleDisposableObserver<List<StoryDTO>> {
        @Override
        public void onNext(List<StoryDTO> storyDTOS) {
            getView().showRefresh(false);
            adapterModel.setNewData(StoryItemUtil.createSectionFromStory(storyDTOS), false);
            getView().onRecyclerViewAdapterUpdated();
        }

        @Override
        public void onError(Throwable e) {
            getView().showRefresh(false);
            super.onError(e);
        }
    }


}
