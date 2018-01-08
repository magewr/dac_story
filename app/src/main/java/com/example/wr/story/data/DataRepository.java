package com.example.wr.story.data;

import com.example.wr.story.data.local.LocalRepository;
import com.example.wr.story.data.remote.RemoteRepository;
import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by WR.
 */

@Singleton
public class DataRepository {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    DataRepository(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Observable<List<StoryDTO>> getStoryDtoList(boolean isSampleData) {
        if (isSampleData)
            return localRepository.getSampleStoryDTOList();
        return localRepository.getStoryDTOList();
    }

    public Observable<StoryDTO> getStoryDtoById(int storyId) {
        return localRepository.getStoryDTOById(storyId);
    }

    public Completable updateStoryDto(StoryDTO item) {
        return localRepository.updateStoryDTO(item);
    }

}
