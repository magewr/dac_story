package com.example.wr.story.ui.content.main.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.wr.story.data.local.dto.StoryDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WR.
 */

public class StorySection extends SectionEntity<StoryDTO> {
    @Getter @Setter int count;

    public StorySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public StorySection(StoryDTO storyDTO) {
        super(storyDTO);
    }
}
