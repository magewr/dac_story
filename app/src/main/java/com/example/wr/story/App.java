package com.example.wr.story;

import android.app.Application;
import android.content.Context;

import com.example.wr.story.di.component.ApplicationComponent;
import com.example.wr.story.di.component.DaggerApplicationComponent;
import com.example.wr.story.di.module.ApiModule;
import com.example.wr.story.di.module.ApplicationModule;

import io.realm.Realm;
import lombok.Getter;

/**
 * Created by WR.
 */

public class App extends Application {

    private ApplicationComponent applicationComponent;
    @Getter private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .apiModule(new ApiModule())
                .applicationModule(new ApplicationModule(this))
                .build();
        context = this;
        Realm.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static App get(Context context) {
        return (App)context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
