package com.example.wr.story.ui.content.gallery.fewimage;

import android.graphics.Bitmap;

import com.example.wr.story.ui.base.BaseView;

import java.util.List;

/**
 * Created by WR on 2018-01-14.
 */

public interface GalleryContract {

    interface View extends BaseView {
        void onBitmapReady(Bitmap bitmap);
        void moveToClickedImage();
    }

    interface Presenter {
        void loadBitmapFromImagePathList(List<String> list);
    }
}
