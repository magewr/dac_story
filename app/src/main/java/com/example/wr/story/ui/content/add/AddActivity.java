package com.example.wr.story.ui.content.add;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.content.detail.DetailActivity;
import com.example.wr.story.ui.content.detail.adapter.ThumbnailViewPagerAdapter;
import com.example.wr.story.ui.listener.OnItemClickListener;
import com.example.wr.story.ui.listener.OnStoryDisplayModeChangedListener.DisplayMode;
import com.example.wr.story.ui.util.Navigator;

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
        adapter = new ThumbnailViewPagerAdapter(getSupportFragmentManager(), DisplayMode.EditMode, new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Navigator.toGalleryActivity(AddActivity.this, newItem.getImagePathList() , position);
            }

            @Override
            public void onRemoveItemClick(int position) {
                newItem.getImagePathList().remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAddItemClick() {
                Toast.makeText(AddActivity.this, "addItemClicked", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setImagePathList(newItem.getImagePathList());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected boolean handleOnBackPressed() {
        return false;
    }

    @Override
    protected DisplayMode getInitDisplayMode() {
        return DisplayMode.EditMode;
    }

    @Override
    protected void onEditFabClicked() {
        newItem.setDate(new Date());
        newItem.setTitle(titleEditText.getText().toString());
        newItem.setMemo(memoEditText.getText().toString());
        presenter.onStoryItemModified(newItem, () -> {

        }, errorMessage -> {

        });
    }
}
