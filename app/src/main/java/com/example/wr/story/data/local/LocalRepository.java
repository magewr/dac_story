package com.example.wr.story.data.local;

import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by WR on 2018-01-11.
 */

public interface LocalRepository {
    Observable<List<StoryDTO>> getStoryDTOList();
    Single<StoryDTO> getStoryDTOById(long id);
    Single<List<StoryDTO>> getStoryListByString(String string);
    Completable updateStoryDTO(StoryDTO newItem);
    Completable addStoryDTO(StoryDTO newItem);
    Completable removeStoryDTO(StoryDTO targetItem);

    Observable<List<StoryDTO>> getSampleStoryDTOList();
}
