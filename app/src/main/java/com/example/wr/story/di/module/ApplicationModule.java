package com.example.wr.story.di.module;

import android.content.Context;

import com.example.wr.story.App;
import com.example.wr.story.data.DataRepository;
import com.example.wr.story.di.scope.PerActivity;
import com.example.wr.story.ui.util.Navigator;
import com.example.wr.story.ui.util.StoryItemUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WR.
 */

@Singleton
@Module
public class ApplicationModule {
    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    StoryItemUtil provideStoryItemUtil() {
        return new StoryItemUtil();
    }

}
