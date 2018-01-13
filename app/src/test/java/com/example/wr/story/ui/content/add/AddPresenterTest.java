package com.example.wr.story.ui.content.add;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.AddStoryCU;
import com.example.wr.story.TestStoryItemGenerator;
import com.example.wr.story.ui.listener.PresenterResultListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by WR on 2018-01-12.
 */

@RunWith(MockitoJUnitRunner.class)
public class AddPresenterTest {

    private AddPresenter addPresenter;

    @Mock
    AddStoryCU addStory;
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
