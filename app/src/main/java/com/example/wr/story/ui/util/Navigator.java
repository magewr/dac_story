package com.example.wr.story.ui.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.example.wr.story.ui.content.add.AddActivity;
import com.example.wr.story.ui.content.camera.CameraActivity;
import com.example.wr.story.ui.content.detail.DetailActivity;
import com.example.wr.story.ui.content.gallery.GalleryActivity;
import com.example.wr.story.ui.content.main.MainActivity;

import java.util.ArrayList;

/**
 * Created by WR.
 */

public class Navigator {
    private Navigator(){}

    public static void toMainActivity(Context context) {
        context.startActivity(MainActivity.getCallingIntent(context));
    }

    public static void toDetailActivity(Context context, long storyId) {
        context.startActivity(DetailActivity.getCallingIntent(context, storyId));
    }

    public static void toGalleryActivity(Context context, ArrayList<String> imagePathList, int imageIndex) {
        context.startActivity(GalleryActivity.getCallingIntent(context, imagePathList, imageIndex));
    }

    public static void toAddActivity(Context context) {
        context.startActivity(AddActivity.getCallingIntent(context));
    }

    public static void toCameraActivityForResult(AppCompatActivity activity) {
        activity.startActivityForResult(CameraActivity.getCallingIntent(activity), 1);
    }

    public static void toPermissionSettingApp(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + context.getPackageName()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
