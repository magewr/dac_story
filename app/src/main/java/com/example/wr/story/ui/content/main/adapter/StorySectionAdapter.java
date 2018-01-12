package com.example.wr.story.ui.content.main.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wr.story.R;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.util.StoryItemUtil;

import java.util.List;

/**
 * Created by WR.
 */

public class StorySectionAdapter extends BaseSectionQuickAdapter<StorySection, BaseViewHolder> implements StorySectionAdapterModel<StorySection> {

    public StorySectionAdapter(int layoutResId, int sectionHeadResId, List<StorySection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, StorySection item) {
        //Section의 onBindViewHolder와 같은 기능의 메소드
        helper.setText(R.id.header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, StorySection item) {
        //Item의 onBindViewHolder와 같은 기능의 메소드
        helper.setText(R.id.story_content_title, item.t.getTitle());
        helper.setText(R.id.story_content_date, StoryItemUtil.getSimpleDateString(item.t.getDate()));
        String imagePath = item.t.getImagePathList().get(0);
        StoryItemUtil.setThumbnailImageByGlide(imagePath, helper.getView(R.id.story_content_image));
        helper.addOnClickListener(R.id.story_content_remove_image);
    }


    @Override
    public StoryDTO getStoryItem(int position) {
        return getData().get(position).t;
    }

    @Override
    public void setNewData(List<StorySection> data, boolean animation) {
        // 오픈소스 자체 기능으로 보이는데
        // setNewData를 할 경우 설정된 Animation이 동작하나 Clear->add는 Animation이 동작되지 않음
        // Observer의 next가 들어올때마다 리스트가 갱신되므로 이 경우는 Animation 동작 안되도록 구현
        if (animation)
            setNewData(data);
        else {
            getData().clear();
            addData(data);
        }

    }
}
