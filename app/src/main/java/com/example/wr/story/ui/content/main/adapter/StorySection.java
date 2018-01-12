package com.example.wr.story.ui.content.main.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.example.wr.story.data.local.dto.StoryDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by WR.
 * 오픈소스 BRVAH의 Section Adapter를 사용하기 위한 Wrapper 클래스
 */

public class StorySection extends SectionEntity<StoryDTO> {
    public StorySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public StorySection(StoryDTO storyDTO) {
        super(storyDTO);
    }
}
