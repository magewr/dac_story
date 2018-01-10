package com.example.wr.story.ui.exception;

/**
 * Created by WR on 2018-01-11.
 */

public class NoPictureException extends IllegalArgumentException {

    @Override
    public String getMessage() {
        return "사진은 반드시 최소 한 장 이상 존재하여야 합니다.";
    }
}
