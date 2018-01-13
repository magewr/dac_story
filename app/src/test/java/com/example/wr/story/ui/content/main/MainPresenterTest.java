package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryListOU;
import com.example.wr.story.interactor.GetStoryListByStringSU;
import com.example.wr.story.interactor.RemoveStoryCU;
import com.example.wr.story.TestStoryItemGenerator;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by WR on 2018-01-09.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mainPresenter;

    @Mock private GetStoryListOU getStoryList;
    @Mock private RemoveStoryCU removeStory;
    @Mock private GetStoryListByStringSU getStoryListByString;
    @Mock private MainContract.View view;
    @Mock private StorySectionAdapterModel adapterModel;

    @Mock private StoryDTO storyItem;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(getStoryList, removeStory, getStoryListByString);
        mainPresenter.setView(view);
        mainPresenter.setAdapterModel(adapterModel);
        TestStoryItemGenerator generator = new TestStoryItemGenerator();
        storyItem = generator.generateStoryDTO();
        when(adapterModel.getStoryItem(anyInt())).thenReturn(storyItem);
    }

    @Test
    public void testOnItemClicked() {
        mainPresenter.onStoryItemSelected(0);
        verify(view).showDetailActivityByStoryId(storyItem.getId());
    }

    @Test
    public void testHandleBackPressed() {
        // 검색창이 포커스가 가지고 있을 경우에는 백프레스가 들어와도 종료되면 안된다.
        when(view.setSearchFocusIfChangeable(false)).thenReturn(true);
        when(view.hasSearchViewQueryString()).thenReturn(false);
        mainPresenter.handleOnBackPressed();
        verify(view, never()).showFinishAlertDialog();

        // 검색창에 Query가 있을 경우 백프레스가 들어오면 Query 를 제거하고 종료되면 안된다.
        when(view.setSearchFocusIfChangeable(false)).thenReturn(false);
        when(view.hasSearchViewQueryString()).thenReturn(true);
        mainPresenter.handleOnBackPressed();
        verify(view).clearSearchVIewQueryString();
        verify(view, never()).showFinishAlertDialog();

        // 그 외의 경우에는 종료 확인 AlertDialog를 띄운다.
        when(view.setSearchFocusIfChangeable(false)).thenReturn(false);
        when(view.hasSearchViewQueryString()).thenReturn(false);
        mainPresenter.handleOnBackPressed();
        verify(view).showFinishAlertDialog();
    }

}
