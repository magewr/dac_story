package com.example.wr.story.ui.content.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wr.story.R;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.main.adapter.StorySectionAdapter;
import com.example.wr.story.ui.listener.HidingScrollListener;
import com.example.wr.story.ui.util.AndroidUtil;
import com.example.wr.story.ui.util.Navigator;
import com.github.clans.fab.FloatingActionMenu;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by WR.
 */

public class MainActivity extends BaseActivity implements MainContract.View, AppBarLayout.OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener {

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Inject MainPresenter presenter;
    private StorySectionAdapter adapter;

    @BindView(R.id.story_recyclerview)      RecyclerView storyRecyclerView;
    @BindView(R.id.fab_menu)                FloatingActionMenu fabMenu;
    @BindView(R.id.floating_search_view)    FloatingSearchView mSearchView;
    @BindView(R.id.appbar)                  AppBarLayout mAppBar;
    @BindView(R.id.story_refresh_layout)    SwipeRefreshLayout swipeRefreshLayout;

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
        initSearchView();
        fabMenu.setClosedOnTouchOutside(true);
        mAppBar.addOnOffsetChangedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRecyclerViewAdapterUpdated() {
    }

    private void initRecyclerView() {
        storyRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //Adapter 바인딩될 뷰 설정
        adapter = new StorySectionAdapter(R.layout.recyclerview_story_item_content, R.layout.recyclerview_story_section_header, null);
        adapter.openLoadAnimation();
        adapter.setEmptyView(R.layout.recyclerview_noitem_bg, (ViewGroup)storyRecyclerView.getParent());

        //Adapter 클릭 리스너 설정
        adapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) ->
                presenter.onStoryItemSelected(position));
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            presenter.removeStoryItem(position, (isSuccess, msg) -> {
                if (isSuccess)
                    Toast.makeText(this, getString(R.string.main_toast_remove_success), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, getString(R.string.main_toast_remove_error) + msg, Toast.LENGTH_SHORT).show();
            });
        });
        storyRecyclerView.setAdapter(adapter);

        //Adapter 스크롤리스너 설정(아래로 스크롤 시 FAB 숨기고 위로 스크롤 시 나타남)
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

        //Presenter에 Adapter 제공
        presenter.setAdapterModel(adapter);
    }

    private void initSearchView () {
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> presenter.searchStory(newQuery));
    }

    @Override
    public void showDetailActivityByStoryId(long storyId) {
        Navigator.toDetailActivity(this, storyId);
    }

    @Override
    public boolean setSearchFocusIfChangeable(boolean focus) {
        return mSearchView.setSearchFocused(focus);
    }

    @Override
    public String getSearchViewQueryString() {
        return mSearchView.getQuery();
    }

    @Override
    public void clearSearchVIewQueryString() {
        mSearchView.clearQuery();
        presenter.getStoryList();
    }

    @Override
    public void showFinishAlertDialog() {
        AndroidUtil.showAlertDialog(this, R.string.main_dialog_exit_message, (dialogInterface, i) -> finish());
    }

    @Override
    public void showRefresh(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @OnClick({R.id.add_new_story, R.id.add_sample_data})
    public void onFabItemClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_story:
                Navigator.toAddActivity(this);
                break;

            case R.id.add_sample_data:
                presenter.getSampleStoryList();
                break;
        }
        fabMenu.close(true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //스크롤 상하 변동 시 searchview도 동일하게 이동
        mSearchView.setTranslationY(verticalOffset);
    }

    @Override
    public void onBackPressed() {
        //Presenter에 BackPressed 처리
        presenter.handleOnBackPressed();
    }

    //SwipeRefreshLayout Implements
    @Override
    public void onRefresh() {
        presenter.handleOnSwipeRefresh();
    }
}
