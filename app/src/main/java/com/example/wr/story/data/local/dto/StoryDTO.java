package com.example.wr.story.data.local.dto;

import java.util.Date;
import java.util.List;

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
    private List<String> imagePathList;
}
