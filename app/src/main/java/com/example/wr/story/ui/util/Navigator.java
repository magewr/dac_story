package com.example.wr.story.ui.util;

import android.content.Context;

import com.example.wr.story.ui.content.detail.DetailActivity;
import com.example.wr.story.ui.content.detail.gallery.GalleryActivity;
import com.example.wr.story.ui.content.main.MainActivity;

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

    public static void toGalleryActivity(Context context, int storyId, int imageIndex) {
        context.startActivity(GalleryActivity.getCallingIntent(context, storyId, imageIndex));
    }
}
