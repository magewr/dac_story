package com.example.wr.story.ui.content.detail.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.wr.story.ui.listener.OnItemClickListener;

import java.util.List;

import lombok.Setter;

/**
 * Created by WR on 2018-01-08.
 */

public class ThumbnailViewPagerAdapter extends FragmentStatePagerAdapter {

    @Setter
    List<String> imagePathList;

    private OnItemClickListener onItemClickListener;

    public ThumbnailViewPagerAdapter(FragmentManager fm, OnItemClickListener onItemClickListener) {
        super(fm);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerImageItemFragment.newInstance(position, imagePathList.get(position), onItemClickListener);
    }

    @Override
    public int getCount() {
        if (imagePathList == null)
            return 0;
        return imagePathList.size();
    }
}
