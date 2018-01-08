package com.example.wr.story.ui.content.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.github.clans.fab.FloatingActionButton;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailContract.View {

    private static final String STORY_ID = "storyId";
    public static Intent getCallingIntent(Context context, int storyId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
        return intent;
    }

    @BindView(R.id.text_date)       TextView dateTextView;
    @BindView(R.id.edit_title)      EditText titleEditText;
    @BindView(R.id.edit_memo)       EditText memoEditText;
    @BindView(R.id.detail_edit_fab) FloatingActionButton editFab;
    @BindView(R.id.image_viewpager) ViewPager viewPager;

    @Inject
    DetailPresenter presenter;

    @Inject
    StoryItemUtil storyItemUtil;

    enum DisplayMode {
        EditMode,
        ShowMode,
    }
    private DisplayMode currentDisplayMode;
    private StoryDTO storyItem;
    private ThumbnailViewPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int storyId = getIntent().getIntExtra(STORY_ID, -1);
        presenter.setStoryById(storyId);
        adapter = new ThumbnailViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        currentDisplayMode = DisplayMode.ShowMode;
    }

    @Override
    public void onGetStory(StoryDTO item) {
        this.storyItem = item;
        titleEditText.setText(item.getTitle());
        memoEditText.setText(item.getMemo());
        dateTextView.setText(storyItemUtil.getDateString(item.getDate()));
        adapter.setImagePathList(item.getImagePathList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public ThumbnailViewPagerAdapter getThumbnailAdapter() {
        return adapter;
    }

    @OnClick(R.id.detail_edit_fab)
    void onEditFabClicked() {
        switch (currentDisplayMode) {
            case EditMode:
                changeModeToShowMode();
                currentDisplayMode = DisplayMode.ShowMode;
                break;
            case ShowMode:
                changeModeToEditMode();
                currentDisplayMode = DisplayMode.EditMode;
                break;
        }
    }

    private void changeModeToEditMode() {
        titleEditText.setEnabled(true);
        memoEditText.setFocusable(true);
        memoEditText.setFocusableInTouchMode(true);
        editFab.setImageResource(R.drawable.done_white);
    }

    private void changeModeToShowMode() {
        titleEditText.setEnabled(false);
        memoEditText.setFocusable(false);
        memoEditText.setFocusableInTouchMode(false);
        editFab.setImageResource(R.drawable.edit_white);

        storyItem.setDate(new Date());
        storyItem.setTitle(titleEditText.getText().toString());
        storyItem.setMemo(memoEditText.getText().toString());

        presenter.onStoryItemModified(storyItem, new PresenterResultListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_error) + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
