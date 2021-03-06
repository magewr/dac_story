package com.example.wr.story.data;

import com.example.wr.story.data.local.FileManager;
import com.example.wr.story.data.local.LocalRepository;
import com.example.wr.story.data.remote.RemoteRepository;
import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by WR.
 */

@Singleton
public class DataRepository {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;
    @Inject FileManager fileManager;

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

    public Single<StoryDTO> getStoryDtoById(long storyId) {
        return localRepository.getStoryDTOById(storyId);
    }

    public Single<List<StoryDTO>> getStoryListByString(String string) {
        return localRepository.getStoryListByString(string);
    }

    public Completable updateStoryDto(StoryDTO item) {
        return localRepository.updateStoryDTO(item);
    }

    public Completable addStoryDto(StoryDTO item) {
        return localRepository.addStoryDTO(item);
    }

    public Completable removeStoryDto(StoryDTO item) {
        return localRepository.removeStoryDTO(item);
    }

    public Single<String> savePictureData (String imagePath, final byte[] data) {
        return fileManager.savePictureData(imagePath, data);
    }

}
