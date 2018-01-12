package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryById;
import com.example.wr.story.interactor.UpdateStory;
import com.example.wr.story.ui.content.TestStoryItemGenerator;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.listener.SimpleDisposableCompletableObserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by WR on 2018-01-12.
 */
@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {
    private DetailPresenter detailPresenter;

    @Mock DetailContract.View view;
    @Mock GetStoryById getStoryById;
    @Mock UpdateStory updateStory;
    @Mock ThumbnailViewPagerAdapterModel adapterModel;
    @Mock PresenterResultListener presenterResultListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        detailPresenter = new DetailPresenter(getStoryById, updateStory);
        detailPresenter.setView(view);
        detailPresenter.setAdapterModel(adapterModel);
    }

    @Test
    public void addPictureOnViewModeTest() {
        detailPresenter.currentDisplayMode = DisplayMode.ViewMode;
        ArrayList<String> imagePath = new ArrayList<>();
        imagePath.add("test1");
        imagePath.add("test2");
        detailPresenter.onPictureAdded(imagePath);
        verify(adapterModel, never()).addImagePathList(any());
    }

    @Test
    public void updateOnViewModeTest() {
        detailPresenter.currentDisplayMode = DisplayMode.ViewMode;
        ArrayList<String> imagePath = new ArrayList<>();
        imagePath.add("test1");
        imagePath.add("test2");
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        detailPresenter.updateStory(item, presenterResultListener);
        verify(updateStory, never()).execute(any(SimpleDisposableCompletableObserver.class), any(StoryDTO.class));
    }

    @Test
    public void updateWithNoPictureItem() {
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        ArrayList<String> imagePath = new ArrayList<>();
        item.setImagePathList(imagePath);
        detailPresenter.updateStory(item, presenterResultListener);
        verify(updateStory, never()).execute(any(SimpleDisposableCompletableObserver.class), any(StoryDTO.class));
    }

    @Test
    public void calculateIndicatorTest() {
        when(adapterModel.getImageCount()).thenReturn(5);
        detailPresenter.calculatePageIndicatorIndex(0);
        verify(view).setViewPagerIndicatorText(1, 5);
        detailPresenter.calculatePageIndicatorIndex(5);
        verify(view).setViewPagerIndicatorText(5,5);
    }

    @Test
    public void onBackPressedHandleTest() {
        detailPresenter.currentDisplayMode = DisplayMode.ViewMode;
        detailPresenter.handleOnBackPressed();
        verify(view, never()).showCancelEditingAlertDialog();

        detailPresenter.currentDisplayMode = DisplayMode.EditMode;
        detailPresenter.handleOnBackPressed();
        verify(view).showCancelEditingAlertDialog();
    }
}
