package com.example.wr.story.ui.listener;

/**
 * Created by WR on 2018-01-09.
 */

public interface OnStoryDisplayModeChangedListener {
    enum DisplayMode {
        EditMode,
        ViewMode,
    }

    void onDisplayModeChanged (DisplayMode displayMode);
}
