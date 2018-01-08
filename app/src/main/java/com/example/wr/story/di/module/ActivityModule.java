package com.example.wr.story.di.module;

import android.content.Context;

import com.example.wr.story.di.scope.PerActivity;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.util.StoryItemUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WR.
 */

@Module
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule (BaseActivity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return activity;
    }

}
