package com.example.wr.story.ui.content.add;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.AddStory;
import com.example.wr.story.ui.content.TestStoryItemGenerator;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.listener.SimpleDisposableCompletableObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by WR on 2018-01-12.
 */

@RunWith(MockitoJUnitRunner.class)
public class AddPresenterTest {

    private AddPresenter addPresenter;

    @Mock AddStory addStory;
    @Mock AddContract.View view;
    @Mock PresenterResultListener presenterResultListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        addPresenter = new AddPresenter(addStory);
        addPresenter.setView(view);
    }

    @Test
    public void addWithEmptyImagePathItemTest() {
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        item.setImagePathList(new ArrayList<>());
        addPresenter.onStoryItemModified(item, presenterResultListener);
        verify(presenterResultListener).onResult(eq(false), anyString());
    }

}
