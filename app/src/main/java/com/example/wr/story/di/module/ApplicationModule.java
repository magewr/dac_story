package com.example.wr.story.di.module;

import android.content.Context;

import com.example.wr.story.App;
import com.example.wr.story.data.local.FileManager;
import com.example.wr.story.data.local.LocalRepository;
import com.example.wr.story.data.local.impl.FileManagerImpl;
import com.example.wr.story.data.local.impl.RealmLocalRepository;

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
    FileManager provideFileManager() {return new FileManagerImpl();}

    @Provides
    @Singleton
    LocalRepository provideLocalRepository() {return new RealmLocalRepository();}

}
