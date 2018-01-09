package com.example.wr.story.ui.content.detail.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wr.story.ui.listener.OnItemClickListener;
import com.example.wr.story.ui.listener.OnStoryDisplayModeChangedListener;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

/**
 * Created by WR on 2018-01-08.
 */

public class ThumbnailViewPagerAdapter extends FragmentStatePagerAdapter implements OnStoryDisplayModeChangedListener {

    @Setter
    List<String> imagePathList;

    private DisplayMode displayMode;

    private OnItemClickListener onItemClickListener;

    public ThumbnailViewPagerAdapter(FragmentManager fm, DisplayMode displayMode, OnItemClickListener onItemClickListener) {
        super(fm);
        this.onItemClickListener = onItemClickListener;
        this.displayMode = displayMode;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == imagePathList.size())
            return ViewPagerImageItemFragment.newAddItemInstance(onItemClickListener);
        else
            return ViewPagerImageItemFragment.newInstance(position, imagePathList.get(position), displayMode, onItemClickListener);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        int size;
        if (imagePathList == null)
            size = 0;
        else
            size = imagePathList.size();

        if (displayMode == DisplayMode.EditMode)
            size += 1;

        return size;
    }

    @Override
    public void onDisplayModeChanged(DisplayMode displayMode) {
        this.displayMode = displayMode;
        notifyDataSetChanged();
    }
}
