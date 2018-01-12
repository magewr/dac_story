package com.example.wr.story.ui.content.main.adapter;

import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.List;

/**
 * Created by WR on 2018-01-12.
 * Presenter에서 Adapter의 Model 역할에 바로 접근하기 위한 인터페이스
 */

public interface StorySectionAdapterModel<T> {
    StoryDTO getStoryItem(int position);
    void setNewData(List<T> datas, boolean animation);
}
