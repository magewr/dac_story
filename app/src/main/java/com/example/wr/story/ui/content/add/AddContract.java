package com.example.wr.story.ui.content.add;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR.
 */

public interface AddContract {

    interface View extends BaseView {
    }

    interface Presenter {
        void onStoryItemModified(StoryDTO item, PresenterResultListener.OnSuccessListener onSuccessListener, PresenterResultListener.OnErrorListener onErrorListener);
    }
}
