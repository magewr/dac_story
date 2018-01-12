package com.example.wr.story.ui.content.main.adapter;

import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.List;

/**
 * Created by WR on 2018-01-12.
 */

public interface StorySectionAdapterModel<T> {
    StoryDTO getStoryItem(int position);
    void setNewData(List<T> datas, boolean animation);
}
