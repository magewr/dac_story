package com.example.wr.story.ui.content.detail.adapter;

import com.example.wr.story.ui.content.detail.DisplayMode;

import java.util.List;

/**
 * Created by WR on 2018-01-12.
 */

public interface ThumbnailViewPagerAdapterModel {
    void addImagePathList (List<String> addItemList);
    void setImagePathList (List<String> itemList);
    void onDisplayModeChanged (DisplayMode displayMode);
    int getImageCount();
}
