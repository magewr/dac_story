package com.example.wr.story.ui.content.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WR on 2018-01-08.
 */

public class GalleryActivity extends BaseActivity {

    private static final String CLICKED_INDEX = "clickedIndex";
    private static final String IMAGE_LIST = "imageList";
    public static Intent getCallingIntent(Context context, ArrayList<String> imageList, int imageIndex) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putStringArrayListExtra(IMAGE_LIST, imageList);
        intent.putExtra(CLICKED_INDEX, imageIndex);
        return intent;
    }

    @BindView(R.id.scroll_gallery_view) ScrollGalleryView scrollGalleryView;
    private int clickedIndex;
    private List<String> imagePathList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initDagger() {
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        StoryItemUtil.getBitmapObservableFromImagePathList(imagePathList)
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
