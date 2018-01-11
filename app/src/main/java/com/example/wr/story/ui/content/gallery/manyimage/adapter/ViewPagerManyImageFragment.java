package com.example.wr.story.ui.content.gallery.manyimage.adapter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wr.story.R;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WR on 2018-01-11.
 */

public class ViewPagerManyImageFragment extends Fragment {

    private String imagePath;

    static ViewPagerManyImageFragment newInstance (String imagePath) {
        ViewPagerManyImageFragment instance = new ViewPagerManyImageFragment();
        instance.imagePath = imagePath;
        return instance;
    }

    @BindView(R.id.big_image_view)
    BigImageView bigImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item_big_gallery_image, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bigImageView.showImage(StoryItemUtil.getUriFromImagePath(imagePath));
    }
}
