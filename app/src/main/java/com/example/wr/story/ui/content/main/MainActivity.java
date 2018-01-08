package com.example.wr.story.ui.content.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wr.story.R;
import com.example.wr.story.di.component.ActivityComponent;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.detail.DetailActivity;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapter;
import com.example.wr.story.ui.content.main.listener.HidingScrollListener;
import com.github.clans.fab.FloatingActionMenu;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WR.
 */

public class MainActivity extends BaseActivity implements MainContract.View {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Inject
    MainPresenter presenter;

    private StorySectionAdapter adapter;

    @BindView(R.id.story_recyclerview)
    RecyclerView storyRecyclerView;

    @BindView(R.id.fab_menu)
    FloatingActionMenu fabMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
        super.onCreate(savedInstanceState);
        initRecyclerView();
    }

    @Override
    public StorySectionAdapter getRecyclerViewAdapter() {
        return adapter;
    }

    @Override
    public void onRecyclerViewAdapterUpdated() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dispose();
    }

    private void initRecyclerView() {
        storyRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new StorySectionAdapter(R.layout.recyclerview_story_item_content, R.layout.recyclerview_story_section_header, null);
        adapter.openLoadAnimation();
        adapter.setEmptyView(R.layout.recyclerview_noitem_bg, (ViewGroup)storyRecyclerView.getParent());
        adapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position)
                -> presenter.onStoryItemSelected(position));
        storyRecyclerView.setAdapter(adapter);
        storyRecyclerView.clearOnScrollListeners();
        storyRecyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                fabMenu.hideMenu(true);
            }

            @Override
            public void onShow() {
                fabMenu.showMenu(true);
            }
        });
    }

    @Override
    public void showDetailActivityByStoryId(int storyId) {
        startActivity(DetailActivity.getCallingIntent(this, storyId));
    }

    @OnClick({R.id.add_new_story, R.id.add_sample_data})
    public void onFabItemClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_story:
                break;

            case R.id.add_sample_data:
                presenter.getSampleStoryList();
                break;
        }
        fabMenu.close(true);
    }
}
