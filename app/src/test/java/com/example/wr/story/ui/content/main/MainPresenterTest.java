package com.example.wr.story.ui.content.main;

import com.example.wr.story.interactor.GetStoryList;
import com.example.wr.story.interactor.RemoveStory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by WR on 2018-01-09.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mainPresenter;

    @Mock private GetStoryList getStoryList;
    @Mock private RemoveStory removeStory;

    @Before
    public void setUp () {
        mainPresenter = new MainPresenter(getStoryList, removeStory);
    }

    @Test
    public void testMainPresenter() {
        mainPresenter.getStoryList();
        Mockito.verify(getStoryList).execute(any(DisposableObserver.class), any(Boolean.class));
    }
}
