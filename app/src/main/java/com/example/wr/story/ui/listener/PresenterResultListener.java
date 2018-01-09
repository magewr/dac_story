package com.example.wr.story.ui.listener;

/**
 * Created by WR on 2018-01-08.
 */

public interface PresenterResultListener {
    interface OnSuccessListener{
        void onSuccess();
    }

    interface OnErrorListener {
        void onError(String errorMessage);
    }
}
