package com.example.wr.story.ui.content.main.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wr.story.App;
import com.example.wr.story.R;

import java.util.List;

/**
 * Created by WR.
 */

public class StorySectionAdapter extends BaseSectionQuickAdapter<StorySection, BaseViewHolder> {

    public StorySectionAdapter(int layoutResId, int sectionHeadResId, List<StorySection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, StorySection item) {
        helper.setText(R.id.header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorySection item) {
        helper.setText(R.id.story_content_title, item.t.getTitle());
        String imagePath = item.t.getImagePathList().get(0);
        if (imagePath.startsWith("story_sample") == true) {
            int index = Integer.parseInt(imagePath.replace("story_sample", ""));
            int[] sampleDrawable = {R.drawable.sample1, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4};
            ImageView imageView = helper.getView(R.id.story_content_image);
            Glide.with(App.getContext())
                    .load(sampleDrawable[index])
                    .thumbnail(0.5f)
                    .into(imageView);
        }
        else {

        }
    }
}
