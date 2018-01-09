package com.example.wr.story.ui.util;

import android.content.Context;

import com.example.wr.story.ui.content.add.AddActivity;
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

    public static void toDetailActivity(Context context, int storyId) {
        context.startActivity(DetailActivity.getCallingIntent(context, storyId));
    }

    public static void toGalleryActivity(Context context, ArrayList<String> imagePathList, int imageIndex) {
        context.startActivity(GalleryActivity.getCallingIntent(context, imagePathList, imageIndex));
    }

    public static void toAddActivity(Context context) {
        context.startActivity(AddActivity.getCallingIntent(context));
    }
}
