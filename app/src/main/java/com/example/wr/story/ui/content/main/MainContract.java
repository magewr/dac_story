package com.example.wr.story.ui.content.main;

import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapter;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapterModel;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR.
 */

public interface MainContract{

    interface View extends BaseView {
        void onRecyclerViewAdapterUpdated();
        void showDetailActivityByStoryId(long storyId);
    }

    interface Presenter {
        void setAdapterModel(StorySectionAdapterModel adapterModel);
        void getStoryList();
        void getSampleStoryList();
        void onStoryItemSelected(int position);
        void removeStoryItem(int position, PresenterResultListener listener);
        void searchStory(String string);
    }
}
