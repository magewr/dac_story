package com.example.wr.story;

import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by WR on 2018-01-12.
 */

public class TestStoryItemGenerator {

    public StoryDTO generateStoryDTO () {
        StoryDTO item = new StoryDTO();
        item.setTitle("TestTitle");
        item.setMemo("TestMemo");
        item.setDate(new Date());
        item.setId(item.getDate().getTime());
        item.setImagePathList(new ArrayList<>());
        item.getImagePathList().add("TestImagePath1");
        item.getImagePathList().add("TestImagePath2");

        return item;
    }
}
