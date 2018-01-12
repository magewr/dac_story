package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;

import java.util.List;

/**
 * Created by WR.
 */

public interface DetailContract {

    interface View extends BaseView {

        /**
         * Presenter가 Repository로부터 자세히보기 StoryItem을 가져왔을 때 불려지는 CallBack
         */
        void onGetStory();

        /**
         * 수정이 취소되어 이전 데이터로 값 원상복구
         */
        void rollbackModifiedStory();

        /**
         * 수정을 취소할 것인지 확인하는 다이얼로그 띄움
         */
        void showCancelEditingAlertDialog();

        /**
         * DisplayMode에 맞게 뷰들을 셋팅
         * @param displayMode currentDisplayMode
         */
        void initViewByDisplayMode(DisplayMode displayMode);

        /**
         * Presenter에서 계산 된 Position으로 Indicator Text 설정
         * @param current
         * @param max
         */
        void setViewPagerIndicatorText(int current, int max);
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
         * Camera로부터 받은 imagePath리스트를 핸들링
         * @param imagePath
         */
        void onPictureAdded(List<String> imagePath);

        /**
         * Repository로부터 받은 현재 StoryItem의 복사본을 View에 제공
         * @return Story의 복사본
         */
        StoryDTO copyDetailStoryItem();

        /**
         * Presenter가 Adapter의 Model 부분에 접근할 수 있도록 View로부터 세팅
         * @param adapterModel Adapter
         */
        void setAdapterModel(ThumbnailViewPagerAdapterModel adapterModel);

        /**
         * 현재 보기모드를 변경
         * @param displayMode toDislpayMode
         */
        void changeDisplayModeTo(DisplayMode displayMode);

        /**
         * 현재 보기모드를 제공
         * @return currnetDisplayMode
         */
        DisplayMode getCurrentDisplayMode();

        /**
         * View에서 BackButton 이벤트를 처리하고 consume 여부 결정
         * @return == true ? 이벤트 consume : to super
         */
        boolean handleOnBackPressed();

        /**
         * ViewPager의 페이지 전환 일어났을 떄 Indicator 인덱스 계산
         * @param position current Index
         */
        void calculatePageIndicatorIndex(int position);
    }
}
