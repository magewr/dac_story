package com.example.wr.story.ui.util;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by WR on 2018-01-11.
 */

public class AndroidUtil {
    public static void setNoStatusBarActivity(AppCompatActivity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
