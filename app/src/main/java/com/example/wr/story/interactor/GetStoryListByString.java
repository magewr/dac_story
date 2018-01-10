package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.interactor.base.SingleUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by WR on 2018-01-10.
 */

public class GetStoryListByString extends SingleUseCase<List<StoryDTO>, String> {

    @Override
    protected Single<List<StoryDTO>> buildUseCaseSingle(String s) {
        return dataRepository.getStoryListByString(s);
    }

    @Inject
    protected GetStoryListByString(DataRepository dataRepository) {
        super(dataRepository);
    }
}
