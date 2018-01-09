package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.base.CompletableUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by WR.
 */

public class AddStory extends CompletableUseCase<StoryDTO> {
    @Inject
    AddStory(DataRepository dataRepository) {
        super(dataRepository);
        this.dataRepository = dataRepository;
    }

    @Override
    protected Completable buildUseCaseCompletable(StoryDTO storyDTO) {
        return dataRepository.addStoryDto(storyDTO);
    }
}
