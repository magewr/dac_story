package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.GetStoryById;
import com.example.wr.story.interactor.UpdateStory;
import com.example.wr.story.ui.base.Presenter;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapterModel;
import com.example.wr.story.ui.exception.NoPictureException;
import com.example.wr.story.ui.exception.StoryNotFoundException;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.listener.SimpleDisposableCompletableObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by WR.
 */

public class DetailPresenter extends Presenter<DetailContract.View> implements DetailContract.Presenter  {

    //UseCase
    GetStoryById getStoryById;
    UpdateStory updateStory;
    //Repository에서 받은 원본 Story Item
    StoryDTO detailStoryItem;
    ThumbnailViewPagerAdapterModel adapterModel;
    //편집/보기모드 Enum
    DisplayMode currentDisplayMode;

    @Inject
    DetailPresenter(GetStoryById getStoryById, UpdateStory updateStory){
        this.getStoryById = getStoryById;
        this.updateStory = updateStory;
    }

    @Override
    public void onCreatePresenter() {
        super.onCreatePresenter();
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        getStoryById.dispose();
        updateStory.dispose();
    }

    @Override
    public void setStoryById(long storyId, PresenterResultListener listener) {
        getStoryById.execute(new DisposableSingleObserver<StoryDTO>() {
            @Override
            public void onSuccess(StoryDTO storyDTO) {
                //Story를 못찾을 경우 에러처리
                if (storyDTO == null) {
                    listener.onResult(false, new StoryNotFoundException().getMessage());
                    return;
                }
                detailStoryItem = storyDTO;
                getView().onGetStory();
                listener.onResult(true, null);
            }

            @Override
            public void onError(Throwable e) {
                listener.onResult(false, e.getMessage());
            }
        }, storyId);
    }

    @Override
    public void updateStory(StoryDTO item, PresenterResultListener listener) {
        // 사진이 하나도 없을경우 저장불가 - 에러처리
        if (item.getImagePathList().size() == 0) {
            listener.onResult(false, new NoPictureException().getMessage());
            return;
        }
        updateStory.execute(new SimpleDisposableCompletableObserver() {
            @Override
            public void onComplete() {
                detailStoryItem = item;
                listener.onResult(true, null);
                adapterModel.setImagePathList(detailStoryItem.getImagePathList());
                // EditMode 종료 후 ViewMode로 변경
                changeDisplayModeTo(DisplayMode.ViewMode);
            }
        }, item);
    }

    @Override
    public void onPictureAdded(List<String> imagePath) {
        adapterModel.addImagePathList(imagePath);
    }

    @Override
    public void setAdapterModel(ThumbnailViewPagerAdapterModel adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public StoryDTO copyDetailStoryItem() {
        return new StoryDTO(detailStoryItem);
    }

    @Override
    public DisplayMode getCurrentDisplayMode() {
        return currentDisplayMode;
    }

    @Override
    public boolean handleOnBackPressed() {
        if (currentDisplayMode == DisplayMode.EditMode) {
            getView().showCancelEditingAlertDialog();
            return true;
        }
        return false;
    }

    @Override
    public void changeDisplayModeTo(DisplayMode displayMode) {
        if (displayMode == currentDisplayMode)
            return;

        currentDisplayMode = displayMode;
        getView().initViewByDisplayMode(currentDisplayMode);
        // ViewPager는 보기모드에따라 삭제 아이콘과 이미지 추가 페이지가 결정되므로 Notify 해 줌.
        adapterModel.onDisplayModeChanged(displayMode);
    }

    @Override
    public void calculatePageIndicatorIndex(int position) {
        int imagePosition = ++position > adapterModel.getImageCount() ? --position : position;
        getView().setViewPagerIndicatorText(imagePosition, adapterModel.getImageCount());
    }
}
