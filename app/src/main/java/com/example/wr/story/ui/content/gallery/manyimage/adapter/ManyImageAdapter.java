package com.example.wr.story.ui.content.gallery.manyimage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import lombok.Setter;

/**
 * Created by WR on 2018-01-11.
 */

public class ManyImageAdapter extends FragmentStatePagerAdapter {

    @Setter
    private List<String> imagePathList;

    public ManyImageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerManyImageFragment.newInstance(imagePathList.get(position));
    }

    @Override
    public int getCount() {
        if (imagePathList == null)
            return 0;
        return imagePathList.size();
    }
}
