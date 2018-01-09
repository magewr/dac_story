package com.example.wr.story.ui.content.detail;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR.
 */

public interface DetailContract {

    interface View extends BaseView {
        void onGetStory();
    }

    interface Presenter {
        void setStoryById(int storyId);
        void onStoryItemModified(StoryDTO item, PresenterResultListener.OnSuccessListener onSuccessListener, PresenterResultListener.OnErrorListener onErrorListener);
        StoryDTO copyDetailStoryItem();
    }
}
