package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryList;
import com.example.wr.story.interactor.GetStoryListByString;
import com.example.wr.story.interactor.RemoveStory;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.TestStoryItemGenerator;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapter;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by WR on 2018-01-09.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mainPresenter;

    @Mock private GetStoryList getStoryList;
    @Mock private RemoveStory removeStory;
    @Mock private GetStoryListByString getStoryListByString;
    @Mock private MainContract.View view;
    @Mock private StorySectionAdapterModel adapterModel;

    @Mock private DisposableCompletableObserver removeCallBack;
    @Mock private MainPresenter.GetStoryListObserver getStoryListCallBack;
    @Mock private DisposableSingleObserver<List<StoryDTO>> getStoryListByStringCallBack;

    @Mock private List<StoryDTO> storyList;
    @Mock private StoryDTO storyItem;
    @Mock private PresenterResultListener presenterResultListener;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(getStoryList, removeStory, getStoryListByString);
        mainPresenter.setView(view);
        mainPresenter.setAdapterModel(adapterModel);
        TestStoryItemGenerator generator = new TestStoryItemGenerator();
        storyItem = generator.generateStoryDTO();
        storyList = new ArrayList<>();
        storyList.add(storyItem);

        doAnswer(invocation -> {
            ((DisposableCompletableObserver) invocation.getArguments()[0]).onComplete();
            return null;
        }).when(removeStory).execute(any(DisposableCompletableObserver.class), any(StoryDTO.class));

        doAnswer(invocation -> {
            ((DisposableSingleObserver<List<StoryDTO>> ) invocation.getArguments()[0]).onSuccess(storyList);
            return null;
        }).when(getStoryListByString).execute(any(DisposableSingleObserver.class), any(String.class));
    }

    @Test
    public void testGetStoryList() {
        doAnswer(invocation -> {
            getStoryListCallBack.onNext(storyList);
            return null;
        }).when(getStoryList).execute(any(MainPresenter.GetStoryListObserver.class), any(Boolean.class));

        mainPresenter.getStoryList();
        verify(mainPresenter.getView()).onRecyclerViewAdapterUpdated();
    }

    @Test
    public void testSearchStory() {
        doAnswer(invocation -> {
            getStoryListByStringCallBack.onSuccess(storyList);
            return null;
        }).when(getStoryListByString).execute(any(DisposableSingleObserver.class), any(String.class));

        mainPresenter.searchStory("test");
        verify(mainPresenter.adapterModel).setNewData(any(List.class), anyBoolean());
    }

    @Test
    public void testRemoveStory() {
        doAnswer(invocation -> {
            removeCallBack.onComplete();
            return null;
        }).when(removeStory).execute(any(DisposableCompletableObserver.class), any(StoryDTO.class));

        mainPresenter.removeStoryItem(0, presenterResultListener);
        verify(presenterResultListener, times(1)).onResult(eq(true), anyString());
        verify(presenterResultListener, never()).onResult(eq(true), anyString());
    }
}
