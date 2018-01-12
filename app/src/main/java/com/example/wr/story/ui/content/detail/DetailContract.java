package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;

import java.util.List;

/**
 * Created by WR.
 */

public interface DetailContract {

    interface View extends BaseView {
        /**
         * Presenter가 Repository로부터 StoryItem을 가져왔을 때 불려지는 CallBack
         */
        void onGetStory();
    }

    interface Presenter {
        /**
         * 이전 Activity로부터 선택된 Story의 ID를 받아온 뒤 Presenter에 item 가져오도록 요청
         * @param storyId 선택된 StoryItem의 id
         * @param listener result listener
         */
        void setStoryById(long storyId, PresenterResultListener listener);

        /**
         * Repository에 수정된 item을 update 요청
         * @param item 수정된 item
         * @param listener result listener
         */
        void updateStory(StoryDTO item, PresenterResultListener listener);

        /**
         * Camera로부터 받은 imagePath를 핸들링하는 메소드
         * @param imagePath
         */
        void onPictureAdded(List<String> imagePath);

        /**
         * Repository로부터 받은 현재 StoryItem의 복사본을 View에 제공하는 메소드
         * @return Story의 복사본
         */
        StoryDTO copyDetailStoryItem();

        /**
         * Presenter가 Adapter의 Model 부분에 접근할 수 있도록 View로부터 세팅받는 메소드
         * @param adapterModel Adapter
         */
        void setAdapterModel(ThumbnailViewPagerAdapterModel adapterModel);
    }
}
