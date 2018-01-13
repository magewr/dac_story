package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.TestStoryItemGenerator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by WR on 2018-01-12.
 */

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {

    @Mock DataRepository dataRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUseCaseTest() {
        AddStoryCU addStory = new AddStoryCU(dataRepository);
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        addStory.buildUseCaseCompletable(item);
        verify(dataRepository).addStoryDto(item);
    }

    @Test
    public void removeUseCaseTest() {
        RemoveStoryCU removeStory = new RemoveStoryCU(dataRepository);
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        removeStory.buildUseCaseCompletable(item);
        verify(dataRepository).removeStoryDto(item);
    }

    @Test
    public void updateUseCaseTest() {
        UpdateStoryCU updateStoryCU = new UpdateStoryCU(dataRepository);
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        updateStoryCU.buildUseCaseCompletable(item);
        verify(dataRepository).updateStoryDto(item);
    }

    @Test
    public void getListUseCaseTest() {
        GetStoryListOU getStoryListOU = new GetStoryListOU(dataRepository);
        getStoryListOU.buildUseCaseObservable(false);
        verify(dataRepository).getStoryDtoList(false);
        //Sample Data List Test
        getStoryListOU.buildUseCaseObservable(true);
        verify(dataRepository).getStoryDtoList(true);
    }

    @Test
    public void searchUseCaseTest() {
        GetStoryListByStringSU getStoryListByStringSU = new GetStoryListByStringSU(dataRepository);
        String string = "test";
        getStoryListByStringSU.buildUseCaseSingle(string);
        verify(dataRepository).getStoryListByString(string);
    }

    @Test
    public void getStoryByIdUseCaseTest() {
        GetStoryByIdSU getStoryListByStringSU = new GetStoryByIdSU(dataRepository);
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        getStoryListByStringSU.buildUseCaseSingle(item.getId());
        verify(dataRepository).getStoryDtoById(item.getId());
    }

}
