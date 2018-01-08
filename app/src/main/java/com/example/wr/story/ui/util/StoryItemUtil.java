package com.example.wr.story.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.content.main.adapter.StorySection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by WR.
 */

public class StoryItemUtil {

    public List<StorySection> createSectionFromStory (List<StoryDTO> dtoList)
    {
        List<StorySection> list = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월");
        String sectionMonth = null;

        for (StoryDTO item : dtoList) {
            String month = df.format(item.getDate());

            if (sectionMonth == null || sectionMonth.equals(month) == false) {
                sectionMonth = month;
                list.add(new StorySection(true, sectionMonth));
            }
            list.add(new StorySection(item));
        }
        return list;
    }

    public String getDateString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 a hh시 mm분 ss초");
        return df.format(date);
    }

}
