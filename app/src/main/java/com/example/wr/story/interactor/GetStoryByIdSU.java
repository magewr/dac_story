package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.base.ObservableUseCase;
import com.example.wr.story.interactor.base.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by WR.
 */

public class GetStoryByIdSU extends SingleUseCase<StoryDTO, Long> {
    @Inject
    GetStoryByIdSU(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    protected Single<StoryDTO> buildUseCaseSingle(Long storyId) {
        return dataRepository.getStoryDtoById(storyId);
    }
}
