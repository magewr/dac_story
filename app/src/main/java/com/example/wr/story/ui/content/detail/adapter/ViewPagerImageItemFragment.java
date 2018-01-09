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
import com.example.wr.story.ui.listener.OnStoryDisplayModeChangedListener.DisplayMode;
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
    private DisplayMode displayMode;
    private boolean isAddPictureItem;

    static ViewPagerImageItemFragment newInstance(int position, String imagePath, DisplayMode displayMode, OnItemClickListener onClickListener) {
        ViewPagerImageItemFragment instance = new ViewPagerImageItemFragment();
        instance.position = position;
        instance.imagePath = imagePath;
        instance.displayMode = displayMode;
        instance.onClickListener = onClickListener;
        instance.isAddPictureItem = false;
        return instance;
    }

    static ViewPagerImageItemFragment newAddItemInstance(OnItemClickListener onClickListener) {
        ViewPagerImageItemFragment instance = new ViewPagerImageItemFragment();
        instance.onClickListener = onClickListener;
        instance.displayMode = DisplayMode.EditMode;
        instance.isAddPictureItem = true;
        return instance;
    }



    @BindView(R.id.detail_image)        ImageView imageView;
    @BindView(R.id.imageview_remove)    ImageView removeImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_detail_image, container, false);
        ButterKnife.bind(this, view);

        switch (displayMode) {
            case ShowMode:
                view.setOnClickListener((v) -> onClickListener.onClick(position));
                removeImageView.setVisibility(View.GONE);
                break;

            case EditMode: {
                if (isAddPictureItem) {
                    view.setOnClickListener((v) -> onClickListener.onAddItemClick());
                    removeImageView.setVisibility(View.GONE);
                    break;
                }
                else {
                    view.setOnClickListener((v) -> onClickListener.onClick(position));
                    removeImageView.setOnClickListener((v) -> onClickListener.onRemoveItemClick(position));
                    removeImageView.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAddPictureItem)
            imageView.setImageResource(R.drawable.add_a_photo);
        else
            StoryItemUtil.setThumbnailImageByGlide(imagePath, imageView);
    }
}
