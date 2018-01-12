package com.example.wr.story.ui.content.main;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryListOU;
import com.example.wr.story.interactor.GetStoryListByStringSU;
import com.example.wr.story.interactor.RemoveStoryCU;
import com.example.wr.story.ui.content.TestStoryItemGenerator;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
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

}
