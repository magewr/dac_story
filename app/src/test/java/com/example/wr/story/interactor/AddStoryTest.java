package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.ui.content.TestStoryItemGenerator;

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
public class AddStoryTest {

    private AddStory addStory;
    @Mock DataRepository dataRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        addStory = new AddStory(dataRepository);
    }

    @Test
    public void addTest() {
        StoryDTO item = new TestStoryItemGenerator().generateStoryDTO();
        addStory.buildUseCaseCompletable(item);
        verify(dataRepository).addStoryDto(item);
    }

}
