package com.example.wr.story.ui.content.detail.gallery;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR.
 */

public interface GalleryContract {

    interface View extends BaseView {
        void onGetStory(StoryDTO item);
    }

    interface Presenter {
        void setStoryById(int storyId);
    }
}
