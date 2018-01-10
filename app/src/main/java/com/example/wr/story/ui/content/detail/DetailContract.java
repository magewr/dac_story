package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.listener.PresenterResultListener;

import java.util.List;

/**
 * Created by WR.
 */

public interface DetailContract {

    interface View extends BaseView {
        void onGetStory();
        ThumbnailViewPagerAdapter getThumbnailAdapter();
    }

    interface Presenter {
        void setStoryById(long storyId);
        void updateStory(StoryDTO item, PresenterResultListener listener);
        void onPictureAdded(List<String> imagePath);
        StoryDTO copyDetailStoryItem();
    }
}
