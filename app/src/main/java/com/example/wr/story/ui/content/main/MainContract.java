package com.example.wr.story.ui.content.main;

import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapter;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR.
 */

public interface MainContract{

    interface View extends BaseView {
        void onRecyclerViewAdapterUpdated();
        StorySectionAdapter getRecyclerViewAdapter();
        void showDetailActivityByStoryId(long storyId);
    }

    interface Presenter {
        void getStoryList();
        void getSampleStoryList();
        void dispose();
        void onStoryItemSelected(int position);
        void removeStoryItem(int position, PresenterResultListener listener);
        void searchStory(String string);
    }
}
