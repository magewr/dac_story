package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.base.ObservableUseCase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by WR.
 */

public class GetStoryById extends ObservableUseCase<StoryDTO, Integer> {
    @Inject
    GetStoryById(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    protected Observable<StoryDTO> buildUseCaseObservable(Integer storyId) {
        return dataRepository.getStoryDtoById(storyId);
    }
}
