package com.example.wr.story.ui.content.detail.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WR on 2018-01-08.
 */

public class GalleryActivity extends BaseActivity implements GalleryContract.View{

    private static final String STORY_ID = "storyId";
    private static final String CLICKED_INDEX = "clickedIndex";
    public static Intent getCallingIntent(Context context, int storyId, int imageIndex) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(STORY_ID, storyId);
        intent.putExtra(CLICKED_INDEX, imageIndex);
        return intent;
    }

    @Inject
    GalleryPresenter presenter;

    @BindView(R.id.scroll_gallery_view) ScrollGalleryView scrollGalleryView;

    private int clickedIndex;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        int storyId = getIntent().getIntExtra(STORY_ID, -1);
        clickedIndex = getIntent().getIntExtra(CLICKED_INDEX, 0);
        presenter.setStoryById(storyId);
    }

    @Override
    public void onGetStory(StoryDTO item) {
        scrollGalleryView
                .setThumbnailSize(300)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager());

        StoryItemUtil.getBitmapObservableFromImagePathList(item.getImagePathList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        scrollGalleryView.addMedia(MediaInfo.mediaLoader(new DefaultImageLoader(bitmap)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GalleryActivity.this, getString(R.string.error) + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        scrollGalleryView.setCurrentItem(clickedIndex);
                    }
                });

    }
}
