package com.example.wr.story.di.component;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.di.module.ApiModule;
import com.example.wr.story.di.module.ApplicationModule;
import com.example.wr.story.ui.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by WR.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    ActivityComponent activityComponent(ActivityModule activityModule);
}
