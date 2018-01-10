package com.example.wr.story.ui.content.main.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wr.story.R;
import com.example.wr.story.ui.util.StoryItemUtil;

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
        helper.setText(R.id.story_content_date, StoryItemUtil.getSimpleDateString(item.t.getDate()));
        String imagePath = item.t.getImagePathList().get(0);
        StoryItemUtil.setThumbnailImageByGlide(imagePath, helper.getView(R.id.story_content_image));
        helper.addOnClickListener(R.id.story_content_remove_image);
    }
}
