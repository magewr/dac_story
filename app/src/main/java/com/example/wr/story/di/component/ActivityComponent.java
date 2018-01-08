package com.example.wr.story.di.component;

import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.di.scope.PerActivity;
import com.example.wr.story.ui.content.detail.DetailActivity;
import com.example.wr.story.ui.content.main.MainActivity;
import com.example.wr.story.ui.content.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * Created by WR.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject (MainActivity activity);
    void inject (SplashActivity activity);
    void inject (DetailActivity activity);
}
