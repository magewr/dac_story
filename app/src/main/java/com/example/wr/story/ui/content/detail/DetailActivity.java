package com.example.wr.story.ui.content.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.listener.OnItemClickListener;
import com.example.wr.story.ui.listener.OnStoryDisplayModeChangedListener.DisplayMode;
import com.example.wr.story.ui.listener.PresenterResultListener;
import com.example.wr.story.ui.util.Navigator;
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

    @Inject    DetailPresenter presenter;
    private    ThumbnailViewPagerAdapter adapter;
    private    DisplayMode currentDisplayMode;
    private    StoryDTO originalStoryItem;
    private    StoryDTO tempStoryItem;


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
        currentDisplayMode = DisplayMode.ShowMode;
        int storyId = getIntent().getIntExtra(STORY_ID, -1);
        presenter.setStoryById(storyId);
        adapter = new ThumbnailViewPagerAdapter(getSupportFragmentManager(), currentDisplayMode, new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Navigator.toGalleryActivity(DetailActivity.this, storyId, position);
            }

            @Override
            public void onRemoveItemClick(int position) {
                tempStoryItem.getImagePathList().remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAddItemClick() {

            }
        });
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (currentDisplayMode == DisplayMode.EditMode) {
            currentDisplayMode = DisplayMode.ShowMode;
            changeModeToShowMode(false);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onGetStory(StoryDTO item) {
        this.originalStoryItem = item;
        tempStoryItem = new StoryDTO(originalStoryItem);
        titleEditText.setText(tempStoryItem.getTitle());
        memoEditText.setText(tempStoryItem.getMemo());
        dateTextView.setText(StoryItemUtil.getDateString(tempStoryItem.getDate()));
        adapter.setImagePathList(tempStoryItem.getImagePathList());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.detail_edit_fab)
    void onEditFabClicked() {
        switch (currentDisplayMode) {
            case EditMode:
                currentDisplayMode = DisplayMode.ShowMode;
                changeModeToShowMode(true);
                break;
            case ShowMode:
                currentDisplayMode = DisplayMode.EditMode;
                changeModeToEditMode();
                break;
        }
    }

    private void changeModeToEditMode() {
        titleEditText.setEnabled(true);
        memoEditText.setFocusable(true);
        memoEditText.setFocusableInTouchMode(true);
        editFab.setImageResource(R.drawable.done_white);
        adapter.onDisplayModeChanged(currentDisplayMode);
    }

    private void changeModeToShowMode(boolean needSave) {
        titleEditText.setEnabled(false);
        memoEditText.setFocusable(false);
        memoEditText.setFocusableInTouchMode(false);
        editFab.setImageResource(R.drawable.edit_white);

        if (needSave) {
            tempStoryItem.setDate(new Date());
            tempStoryItem.setTitle(titleEditText.getText().toString());
            tempStoryItem.setMemo(memoEditText.getText().toString());

            presenter.onStoryItemModified(tempStoryItem, new PresenterResultListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_success), Toast.LENGTH_SHORT).show();
                    originalStoryItem = tempStoryItem;
                    tempStoryItem = new StoryDTO(originalStoryItem);
                    adapter.setImagePathList(tempStoryItem.getImagePathList());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_error) + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            tempStoryItem = new StoryDTO(originalStoryItem);
            adapter.setImagePathList(tempStoryItem.getImagePathList());
            titleEditText.setText(tempStoryItem.getTitle());
            memoEditText.setText(tempStoryItem.getMemo());
        }
        adapter.onDisplayModeChanged(currentDisplayMode);
    }
}
