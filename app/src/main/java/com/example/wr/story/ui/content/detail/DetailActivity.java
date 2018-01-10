package com.example.wr.story.ui.content.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
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
import com.example.wr.story.ui.util.Navigator;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.wr.story.ui.content.camera.CameraActivity.RESULT_IMAGE_LIST_KEY;

public class DetailActivity extends BaseActivity implements DetailContract.View {

    private static final String STORY_ID = "storyId";
    public static Intent getCallingIntent(Context context, long storyId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
        return intent;
    }

    @BindView(R.id.text_date)       protected TextView dateTextView;
    @BindView(R.id.edit_title)      protected EditText titleEditText;
    @BindView(R.id.edit_memo)       protected EditText memoEditText;
    @BindView(R.id.detail_edit_fab) protected FloatingActionButton editFab;
    @BindView(R.id.image_viewpager) protected ViewPager viewPager;

    @Inject       DetailPresenter presenter;
    protected     ThumbnailViewPagerAdapter adapter;
    private       DisplayMode currentDisplayMode;
    private       StoryDTO tempStoryItem;


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
        initOwnData();
        initViewPager();
        changeModeTo(getInitDisplayMode());
    }

    protected void initOwnData() {
        long storyId = getIntent().getLongExtra(STORY_ID, -1);
        presenter.setStoryById(storyId);
    }

    protected void initViewPager() {
        adapter = new ThumbnailViewPagerAdapter(getSupportFragmentManager(), getInitDisplayMode(), new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Navigator.toGalleryActivity(DetailActivity.this, getDetailedStoryItem().getImagePathList(), position);
            }

            @Override
            public void onRemoveItemClick(int position) {
                getDetailedStoryItem().getImagePathList().remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAddItemClick() {
                Navigator.toCameraActivityForResult(DetailActivity.this);
            }
        });
        viewPager.setAdapter(adapter);
    }

    protected DisplayMode getInitDisplayMode() {
        return DisplayMode.ViewMode;
    }

    protected StoryDTO getDetailedStoryItem() {
        return tempStoryItem;
    }

    protected boolean handleOnBackPressed() {
        if (currentDisplayMode == DisplayMode.EditMode) {
            rollbackModifiedStory();
            return true;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        if (handleOnBackPressed() == true)
            return;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ArrayList<String> pathListFromCamera = data.getStringArrayListExtra(RESULT_IMAGE_LIST_KEY);
            if (pathListFromCamera != null) {
                presenter.onPictureAdded(pathListFromCamera);
            }
        }
    }

    @Override
    public ThumbnailViewPagerAdapter getThumbnailAdapter() {
        return adapter;
    }

    @Override
    public void onGetStory() {
        tempStoryItem = presenter.copyDetailStoryItem();
        titleEditText.setText(tempStoryItem.getTitle());
        memoEditText.setText(tempStoryItem.getMemo());
        dateTextView.setText(StoryItemUtil.getDateString(tempStoryItem.getDate()));
        adapter.setImagePathList(tempStoryItem.getImagePathList());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.detail_edit_fab)
    protected void onEditFabClicked() {
        switch (currentDisplayMode) {
            case EditMode:
                saveModifiedStory();
                break;
            case ViewMode:
                changeModeTo(DisplayMode.EditMode);
                break;
        }
    }

    private void saveModifiedStory () {
        tempStoryItem.setDate(new Date());
        tempStoryItem.setTitle(titleEditText.getText().toString());
        tempStoryItem.setMemo(memoEditText.getText().toString());

        presenter.updateStory(tempStoryItem, (isSuccess, msg) -> {
            if (isSuccess) {
                Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_success), Toast.LENGTH_SHORT).show();
                tempStoryItem = presenter.copyDetailStoryItem();
                adapter.setImagePathList(tempStoryItem.getImagePathList());
                changeModeTo(DisplayMode.ViewMode);
            }
            else
                Toast.makeText(DetailActivity.this, getString(R.string.detail_toast_modify_error) + msg, Toast.LENGTH_SHORT).show();
        });
    }

    private void rollbackModifiedStory() {
        tempStoryItem = presenter.copyDetailStoryItem();
        adapter.setImagePathList(tempStoryItem.getImagePathList());
        titleEditText.setText(tempStoryItem.getTitle());
        memoEditText.setText(tempStoryItem.getMemo());
        changeModeTo(DisplayMode.ViewMode);
    }

    private void changeModeTo (DisplayMode displayMode) {
        if (displayMode == currentDisplayMode)
            return;

        currentDisplayMode = displayMode;
        switch (displayMode) {
            case EditMode:
                titleEditText.setEnabled(true);
                memoEditText.setFocusable(true);
                memoEditText.setFocusableInTouchMode(true);
                editFab.setImageResource(R.drawable.done_white);
                dateTextView.setVisibility(View.INVISIBLE);
                break;

            case ViewMode:
                titleEditText.setEnabled(false);
                memoEditText.setFocusable(false);
                memoEditText.setFocusableInTouchMode(false);
                editFab.setImageResource(R.drawable.edit_white);
                dateTextView.setVisibility(View.VISIBLE);
                break;
        }
        adapter.onDisplayModeChanged(displayMode);
    }

}
