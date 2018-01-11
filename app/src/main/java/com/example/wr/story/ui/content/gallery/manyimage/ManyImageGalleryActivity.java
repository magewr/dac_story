package com.example.wr.story.ui.content.gallery.manyimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.example.wr.story.R;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.gallery.manyimage.adapter.ManyImageAdapter;
import com.example.wr.story.ui.util.AndroidUtil;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManyImageGalleryActivity extends BaseActivity {

    private static final String CLICKED_INDEX = "clickedIndex";
    private static final String IMAGE_LIST = "imageList";
    public static Intent getCallingIntent(Context context, ArrayList<String> imageList, int imageIndex) {
        Intent intent = new Intent(context, ManyImageGalleryActivity.class);
        intent.putStringArrayListExtra(IMAGE_LIST, imageList);
        intent.putExtra(CLICKED_INDEX, imageIndex);
        return intent;
    }

    @BindView(R.id.big_gallery_viewpager)
    ViewPager viewPager;
    @BindView(R.id.page_indicator)
    PageIndicatorView indicatorView;

    private ManyImageAdapter adapter;
    private int clickedIndex;
    private List<String> imagePathList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery_acitivity_for_big_image;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidUtil.setNoStatusBarActivity(this);
        super.onCreate(savedInstanceState);
        initFromIntent();
        initViewPager();
    }

    private void initFromIntent() {
        imagePathList = getIntent().getStringArrayListExtra(IMAGE_LIST);
        clickedIndex = getIntent().getIntExtra(CLICKED_INDEX, 0);
    }

    private void initViewPager () {
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));
        adapter = new ManyImageAdapter(getSupportFragmentManager());
        adapter.setImagePathList(imagePathList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                indicatorView.setSelection(position);
            }
        });
        viewPager.setCurrentItem(clickedIndex);
        indicatorView.setCount(imagePathList.size());
        indicatorView.setSelection(clickedIndex);
    }
}
