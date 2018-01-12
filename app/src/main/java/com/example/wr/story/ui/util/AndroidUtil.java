package com.example.wr.story.ui.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by WR on 2018-01-11.
 * 안드로이드 플랫폼에 종속된 Utility한 메소드를 사용하기 위한 클래스
 */

public class AndroidUtil {
    /**
     * 상단 Status바를 없애고 전체화면모드로 변환
     * @param activity activity
     */
    public static void setNoStatusBarActivity(AppCompatActivity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 간단한 AlertDialog를 생성 후 show
     * @param context dialog를 show 할 context
     * @param messageStringId message영역에 보여질 String의 resource id
     * @param positiveListener OK버튼 리스너
     */
    public static void showAlertDialog(Context context, int messageStringId, DialogInterface.OnClickListener positiveListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageStringId);
        builder.setPositiveButton(android.R.string.ok, positiveListener);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }
}
