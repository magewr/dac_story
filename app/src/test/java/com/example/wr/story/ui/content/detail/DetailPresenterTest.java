package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryByIdSU;
import com.example.wr.story.interactor.UpdateStoryCU;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
    @Mock GetStoryByIdSU getStoryById;
    @Mock UpdateStoryCU updateStory;
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
        //보기 모드에서는 사진 추가가 호출되어도 무시되어야 한다.
        detailPresenter.currentDisplayMode = DisplayMode.ViewMode;
        ArrayList<String> imagePath = new ArrayList<>();
        imagePath.add("test1");
        imagePath.add("test2");
        detailPresenter.onPictureAdded(imagePath);
        verify(adapterModel, never()).addImagePathList(any());
    }

    @Test
    public void updateOnViewModeTest() {
        //보기 모드에서는 업데이트 요청이 들어와도 무시되어야 한다.
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
        //사진이 없는 상태에서 업데이트가 들어오면 에러가 리턴되어야 한다.
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        ArrayList<String> imagePath = new ArrayList<>();
        item.setImagePathList(imagePath);
        detailPresenter.updateStory(item, presenterResultListener);
        verify(presenterResultListener).onResult(eq(false), anyString());
    }

    @Test
    public void calculateIndicatorTest() {
        //전체 이미지 5개, 인덱스 0일 경우에 인디케이터는 1, 5가 되어야 한다.
        when(adapterModel.getImageCount()).thenReturn(5);
        detailPresenter.calculatePageIndicatorIndex(0);
        verify(view).setViewPagerIndicatorText(1, 5);
        //전체 이미지 5개, 인덱스 5(사진추가 아이템)일 경우에 인디케이터는 5, 5가 되어야 한다.
        detailPresenter.calculatePageIndicatorIndex(5);
        verify(view).setViewPagerIndicatorText(5,5);
    }

    @Test
    public void onBackPressedHandleTest() {
        //보기 모드에서는 BackPressed가 발생하면 Alert이 뜨지 않아야 한다.
        detailPresenter.currentDisplayMode = DisplayMode.ViewMode;
        detailPresenter.handleOnBackPressed();
        verify(view, never()).showCancelEditingAlertDialog();
        //편집 모드에서는 BackPressed가 발생하면 Alert이 발생해야 한다.
        detailPresenter.currentDisplayMode = DisplayMode.EditMode;
        detailPresenter.handleOnBackPressed();
        verify(view).showCancelEditingAlertDialog();
    }
}
