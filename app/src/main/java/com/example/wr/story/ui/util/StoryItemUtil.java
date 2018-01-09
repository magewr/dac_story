package com.example.wr.story.ui.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wr.story.App;
import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.content.main.adapter.StorySection;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by WR.
 */
public class StoryItemUtil {

    public static List<StorySection> createSectionFromStory (List<StoryDTO> dtoList)
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

    public static String getDateString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 a hh시 mm분 ss초");
        return df.format(date);
    }

    public static String getSimpleDateString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("M/d E a h:mm");
        return df.format(date);
    }

    public static boolean setThumbnailImageByGlide(String imagePath, ImageView imageView) {
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
                Glide.with(App.getContext())
                        .load(new File(imagePath))
                        .thumbnail(0.5f)
                        .into(imageView);
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    public static Bitmap getBitmapFromImagePath(String imagePath) {
        try {
            if (imagePath.startsWith("story_sample") == true) {
                int index = Integer.parseInt(imagePath.replace("story_sample", ""));
                int[] sampleDrawable = {R.drawable.sample1, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4};
                return BitmapFactory.decodeResource(App.getContext().getResources(), sampleDrawable[index]);
            }
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            return bitmap;
        }
        catch (Exception e) {
            return BitmapFactory.decodeResource(App.getContext().getResources(), R.drawable.image_broken);
        }
    }

    public static Observable<Bitmap> getBitmapObservableFromImagePathList(List<String> stringList) {
        Observable<Bitmap> observable = Observable.create(emitter -> {
            try {
                for (String path : stringList) {
                    Bitmap bitmap = getBitmapFromImagePath(path);
                    emitter.onNext(bitmap);
                }
                emitter.onComplete();
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        });
        return observable;
    }
}
