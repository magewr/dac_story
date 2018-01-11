package com.example.wr.story.ui.exception;

/**
 * Created by WR on 2018-01-11.
 */

public class StoryNotFoundException extends IllegalStateException {
    @Override
    public String getMessage() {
        return "Story Item을 Repository에서 찾을 수 없습니다.";
    }
}
