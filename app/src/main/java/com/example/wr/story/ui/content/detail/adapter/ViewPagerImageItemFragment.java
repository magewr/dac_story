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
import com.example.wr.story.ui.listener.OnItemClickListener;
import com.example.wr.story.ui.util.StoryItemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WR on 2018-01-08.
 */

public class ViewPagerImageItemFragment extends Fragment {

    private int position;
    private String imagePath;
    private OnItemClickListener onClickListener;

    static ViewPagerImageItemFragment newInstance(int position, String imagePath, OnItemClickListener onClickListener) {
        ViewPagerImageItemFragment instance = new ViewPagerImageItemFragment();
        instance.position = position;
        instance.imagePath = imagePath;
        instance.onClickListener = onClickListener;
        return instance;
    }

    @BindView(R.id.detail_image) ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_detail_image, container, false);
        ButterKnife.bind(this, view);
        view.setOnClickListener((v) -> onClickListener.onClick(position));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StoryItemUtil.setThumbnailImageByGlide(imagePath, imageView);
    }
}
