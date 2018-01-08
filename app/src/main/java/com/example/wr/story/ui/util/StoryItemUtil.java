package com.example.wr.story.ui.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wr.story.App;
import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.content.main.adapter.StorySection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by WR.
 */
public class StoryItemUtil {

    @Inject
    public StoryItemUtil() {}

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

    public boolean setThumbnailImageByGlide(String imagePath, ImageView imageView) {
        try {
            if (imagePath.startsWith("story_sample") == true) {
                int index = Integer.parseInt(imagePath.replace("story_sample", ""));
                int[] sampleDrawable = {R.drawable.sample1, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4};
                Glide.with(App.getContext())
                        .load(sampleDrawable[index])
                        .thumbnail(0.5f)
                        .into(imageView);
                return true;
            } else {

                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
    }
}
