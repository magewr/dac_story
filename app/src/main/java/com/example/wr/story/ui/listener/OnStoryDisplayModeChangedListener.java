package com.example.wr.story.ui.listener;

/**
 * Created by WR on 2018-01-09.
 */

public interface OnStoryDisplayModeChangedListener {
    enum DisplayMode {
        EditMode,
        ShowMode,
    }

    void onDisplayModeChanged (DisplayMode displayMode);
}
