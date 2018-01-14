package com.example.wr.story.ui.content.gallery.fewimage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.wr.story.R;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.util.AndroidUtil;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by WR on 2018-01-08.
 */

public class GalleryActivity extends BaseActivity implements GalleryContract.View{

    private static final String CLICKED_INDEX = "clickedIndex";
    private static final String IMAGE_LIST = "imageList";
    public static Intent getCallingIntent(Context context, ArrayList<String> imageList, int imageIndex) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putStringArrayListExtra(IMAGE_LIST, imageList);
        intent.putExtra(CLICKED_INDEX, imageIndex);
        return intent;
    }

    @Inject GalleryPresenter presenter;
    @BindView(R.id.scroll_gallery_view) ScrollGalleryView scrollGalleryView;
    private int clickedIndex;
    private List<String> imagePathList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initDagger() {
        activityComponent = getApplicationComponent().activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
    }

    @Override
    protected void initPresenter() {
        super.presenter = presenter;
        presenter.setView(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidUtil.setNoStatusBarActivity(this);
        super.onCreate(savedInstanceState);
        initFromIntent();
        initScrollGalleryView();
    }

    private void initFromIntent() {
        imagePathList = getIntent().getStringArrayListExtra(IMAGE_LIST);
        clickedIndex = getIntent().getIntExtra(CLICKED_INDEX, 0);
    }

    private void initScrollGalleryView() {
        scrollGalleryView
                .setThumbnailSize(300)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager());

        presenter.loadBitmapFromImagePathList(imagePathList);
    }

    @Override
    public void onBitmapReady(Bitmap bitmap) {
        scrollGalleryView.addMedia(MediaInfo.mediaLoader(new DefaultImageLoader(bitmap)));
    }

    @Override
    public void moveToClickedImage() {
        scrollGalleryView.setCurrentItem(clickedIndex);
    }
}
