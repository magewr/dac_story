package com.example.wr.story.ui.content.camera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.story.R;
import com.example.wr.story.di.module.ActivityModule;
import com.example.wr.story.ui.base.BaseActivity;
import com.example.wr.story.ui.util.Navigator;
import com.example.wr.story.ui.util.StoryItemUtil;
import com.google.android.cameraview.CameraView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WR on 2018-01-10.
 */

public class CameraActivity extends BaseActivity implements CameraContract.View{
    public static final String RESULT_IMAGE_LIST_KEY = "resultImageListKey";
    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        return intent;
    }

    @Inject  CameraPresenter presenter;
    private ArrayList<String> imagePathList = new ArrayList<>();
    private int count = 0;

    @BindView(R.id.camera)          CameraView cameraView;
    @BindView(R.id.camera_count)    TextView countText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        requestPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            cameraView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    private void requestPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted)
                        initCameraView();
                    else {
                        Toast.makeText(this, getString(R.string.camera_toast_request_permission), Toast.LENGTH_SHORT).show();
                        Navigator.toPermissionSettingApp(this);
                        finish();
                    }
                });
    }

    private void initCameraView() {
        cameraView.addCallback(callBack);
    }

    private CameraView.Callback callBack = new CameraView.Callback() {
        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            final String fileName = getPictureFilePath(StoryItemUtil.getDateStringForFIle(new Date()));
            presenter.savePicture(fileName, data, (isSuccess, msg) -> {
                if (isSuccess)
                    ;
                else
                    Toast.makeText(CameraActivity.this, getString(R.string.camera_toast_save_error) + msg, Toast.LENGTH_SHORT).show();
            });
        }
    };

    @OnClick(R.id.take_picture)
    public void onTakePicture() {
        cameraView.takePicture();
    }

    @Override
    public void onSavePicture(String imagePath) {
        countText.setText(String.valueOf(++count));
        imagePathList.add(imagePath);
    }

    private String getPictureFilePath(String fileName) {
        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (storage != null && storage.exists()) {
            String folderPath = storage.getPath() + "/Story/";
            File folder = new File(folderPath);
            if (folder.exists() == false)
                folder.mkdir();
            return folderPath + fileName + ".jpg";
        }
        return String.format("%s/%s.jpg",getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath(), fileName);
    }

    private void makeResultIntent() {
        Intent intent = new Intent();
        if (imagePathList.size() > 0) {
            intent.putStringArrayListExtra(RESULT_IMAGE_LIST_KEY, imagePathList);
            setResult(RESULT_OK, intent);
        }
        else
            setResult(RESULT_CANCELED);
    }

    @Override
    public void finish() {
        makeResultIntent();
        super.finish();
    }
}
