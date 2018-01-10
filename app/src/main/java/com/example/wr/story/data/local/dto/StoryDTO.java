package com.example.wr.story.data.local.dto;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by WR.
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoryDTO {
    private int id;
    private Date date;
    private String title;
    private String memo;
    private ArrayList<String> imagePathList;

    public StoryDTO(StoryDTO copyStoryDto) {
        this.id = copyStoryDto.id;
        this.date = copyStoryDto.date;
        this.title = copyStoryDto.title;
        this.memo = copyStoryDto.memo;
        this.imagePathList = new ArrayList<>(copyStoryDto.imagePathList);

    }
}
