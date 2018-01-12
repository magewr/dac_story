package com.example.wr.story.ui.content.add;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.content.detail.DetailActivity;
import com.example.wr.story.ui.content.detail.DisplayMode;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

public class AddActivity extends DetailActivity {
    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, AddActivity.class);
        return intent;
    }

    @Inject     AddPresenter presenter;
    protected   StoryDTO    newItem;

    @Override
    protected void initOwnData() {
        newItem = new StoryDTO();
        newItem.setMemo("");
        newItem.setTitle("");
        newItem.setImagePathList(new ArrayList<>());
    }

    @Override
    protected void initDagger() {
        activityComponent = getApplicationComponent().activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
    }

    @Override
    protected void initViewPager() {
        super.initViewPager();
        adapter.setImagePathList(newItem.getImagePathList());
    }

    @Override
    protected StoryDTO getDetailedStoryItem() {
        return newItem;
    }

    @Override
    protected DisplayMode getInitDisplayMode() {
        return DisplayMode.EditMode;
    }

    @Override
    protected void onEditFabClicked() {
        newItem.setDate(new Date());
        newItem.setId(newItem.getDate().getTime());
        newItem.setTitle(titleEditText.getText().toString());
        newItem.setMemo(memoEditText.getText().toString());
        presenter.onStoryItemModified(newItem, (isSuccess, msg) -> {
            if (isSuccess) {
                Toast.makeText(this, getString(R.string.detail_toast_add_success), Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(this, getString(R.string.detail_toast_add_error) + msg, Toast.LENGTH_SHORT).show();
        });
    }
}
