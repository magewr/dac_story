package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryList;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.util.StoryItemUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by WR.
 */

public class MainPresenter extends Presenter<MainContract.View> implements MainContract.Presenter {

    GetStoryList getStoryList;


    @Inject
    public MainPresenter(GetStoryList getStoryListUseCase){
        this.getStoryList = getStoryListUseCase;
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
