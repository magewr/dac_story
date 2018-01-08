package com.example.wr.story.ui.content.splash;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wr.story.R;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.content.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashContract.View{

    @Inject
    SplashPresenter splashPresenter;

    @BindView(R.id.imgView)
    ImageView splashImageVIew;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initDagger() {
        activityComponent = getApplicationComponent().activityComponent(new ActivityModule(this));
        activityComponent.inject(this);
    }

    @Override
    protected void initPresenter() {
        super.presenter = splashPresenter;
        presenter.setView(this);
    }

    @Override
    public void moveToMainActivity() {
        new Handler().postDelayed(() -> {
            startActivity(MainActivity.getCallingIntent(this));
            finish();
        }, 1000);
    }

    @Override
    public void loadSplashImage() {
        Glide.with(this).load(R.raw.mark_and_john).into(splashImageVIew);
    }
}
