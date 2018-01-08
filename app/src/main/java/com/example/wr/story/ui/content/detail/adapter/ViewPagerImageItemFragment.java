package com.example.wr.story.ui.content.detail.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wr.story.R;
import com.example.wr.story.ui.util.StoryItemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WR on 2018-01-08.
 */

public class ViewPagerImageItemFragment extends Fragment {

    private String imagePath;

    static ViewPagerImageItemFragment newInstance(String imagePaht) {
        ViewPagerImageItemFragment instance = new ViewPagerImageItemFragment();
        instance.imagePath = imagePaht;
        return instance;
    }

    @BindView(R.id.detail_image) ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_detail_image, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StoryItemUtil.setThumbnailImageByGlide(imagePath, imageView);
    }
}
