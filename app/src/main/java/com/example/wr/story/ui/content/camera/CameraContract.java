package com.example.wr.story.ui.content.camera;

import com.example.wr.story.ui.base.BaseView;
import com.example.wr.story.ui.listener.PresenterResultListener;

/**
 * Created by WR on 2018-01-10.
 */

public interface CameraContract {

    interface View extends BaseView{
        void onSavePicture(String imagePath);
    }

    interface Presenter {
        void savePicture(String imagePath, final byte[] data, PresenterResultListener listener);
    }
}
