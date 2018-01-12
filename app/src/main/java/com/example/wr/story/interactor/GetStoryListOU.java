package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.base.ObservableUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by WR.
 */

public class GetStoryListOU extends ObservableUseCase<List<StoryDTO>, Boolean> {
    @Inject
    GetStoryListOU(DataRepository dataRepository) {
        super(dataRepository);
        this.dataRepository = dataRepository;
    }

    @Override
    protected Observable<List<StoryDTO>> buildUseCaseObservable(Boolean isSampleData) {
        return dataRepository.getStoryDtoList(isSampleData.booleanValue());
    }

}
