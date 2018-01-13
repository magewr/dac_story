package com.example.wr.story.ui.content.main;

import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR.
 */

public interface MainContract{

    interface View extends BaseView {
        /**
         * Presenter에 의해 RecyclerView Update 발생 시 불려질 콜백
         */
        void onRecyclerViewAdapterUpdated();

        /**
         * presenter로 부터 Story 자세히보기를 요청하는 메소드
         * @param storyId Item의 Id
         */
        void showDetailActivityByStoryId(long storyId);

        /**
         * SearchFocus를 param으로 변경
         * @param focus focus
         * @return focus isChanged
         */
        boolean setSearchFocusIfChangeable(boolean focus);

        /**
         * SearchView의 QueryString를 제공
         * @return QueryString
         */
        String getSearchViewQueryString();

        /**
         * SearchView의 Query를 지움(초기화)
         */
        void clearSearchVIewQueryString();

        /**
         * 종료 Alert Dialog를 Show
         */
        void showFinishAlertDialog();

        /**
         * Progress Show/Hide
         * @param show show
         */
        void showRefresh(boolean show);
    }

    interface Presenter {
        /**
         * presenter가 Adapter의 Model 영역 접근을위한 인터페이스 제공받는 메소드
         * @param adapterModel StorySectionAdapterModel impl
         */
        void setAdapterModel(StorySectionAdapterModel adapterModel);

        /**
         * repository에 스토리 리스트 요청
         */
        void getStoryList();

        /**
         * repository에 Sample 스토리 리스트 요청
         */
        void getSampleStoryList();

        /**
         * View로부터 Story Item Click 이벤트 전달
         * @param position 선택된 Item의 Position
         */
        void onStoryItemSelected(int position);

        /**
         * View로부터 Story Item Remove Click 이벤트 전달
         * @param position 선택된 Item의 Position
         * @param listener 결과 받을 CallBack
         */
        void removeStoryItem(int position, PresenterResultListener listener);

        /**
         * repository에 제목 또는 메모에 특정 String이 있는 스토리 리스트 요청
         * @param string 검색할 String
         */
        void searchStory(String string);

        /**
         * View에서 BackButtonPressed 이벤트를 처리
         */
        void handleOnBackPressed();

        /**
         * 당겨서 새로고침 이벤트 처리
         */
        void handleOnSwipeRefresh();
    }
}
