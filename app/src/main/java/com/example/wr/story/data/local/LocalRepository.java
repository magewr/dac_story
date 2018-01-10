package com.example.wr.story.data.local;

import com.example.wr.story.data.local.dto.StoryDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by WR.
 */

public class LocalRepository {

    @Inject
    LocalRepository(){}

    static ArrayList<StoryDTO> storyList = new ArrayList<>();
    private BehaviorSubject<List<StoryDTO>> itemListBehaviorSubject = BehaviorSubject.createDefault(storyList);

    public Observable<List<StoryDTO>> getStoryDTOList() {
        return itemListBehaviorSubject;
    }

    private List<StoryDTO> getSortedStoryList() {
        Collections.sort(storyList, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        return storyList;
    }

    public Observable<StoryDTO> getStoryDTOById(int id) {
        Observable<StoryDTO> sampleStoryDTOListObservable = Observable.fromIterable(storyList)
                .filter(item -> item.getId() == id);
        return sampleStoryDTOListObservable;
    }

    public Single<List<StoryDTO>> getStoryListByString(String string) {
        Single<List<StoryDTO>> sampleStoryDTOListSingle = Observable.fromIterable(storyList)
                .filter(item -> item.getTitle().contains(string) || item.getMemo().contains(string))
                .toList();
        return sampleStoryDTOListSingle;
    }

    public Completable updateStoryDTO(StoryDTO newItem) {
        Completable completable = Completable.create(emitter -> {
            try {
                for (int i = 0 ; i < storyList.size() ; i++) {
                    if (storyList.get(i).getId() == newItem.getId()) {
                        storyList.set(i, newItem);
                        itemListBehaviorSubject.onNext(getSortedStoryList());
                        emitter.onComplete();
                        return;
                    }
                }
                emitter.onError(new RuntimeException("target item not exist."));
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        });

        return completable;
    }

    public Completable addStoryDTO(StoryDTO newItem) {
        Completable completable = Completable.create(emitter -> {
            try {
                storyList.add(newItem);
                itemListBehaviorSubject.onNext(getSortedStoryList());
                emitter.onComplete();
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        });

        return completable;
    }

    public Completable removeStoryDTO(StoryDTO targetItem) {
        Completable completable = Completable.create(emitter -> {
            try {
                storyList.remove(targetItem);
                itemListBehaviorSubject.onNext(getSortedStoryList());
                emitter.onComplete();
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        });
        return completable;
    }


    //////////////////////////////////////////////////////
    //    샘플 데이터 제작용 메소드
    //
    public Observable<List<StoryDTO>> getSampleStoryDTOList() {
        storyList.addAll(makeSampleStoryDTOList());
        return getStoryDTOList();
    }

    private List<StoryDTO> makeSampleStoryDTOList() {
        ArrayList<StoryDTO> storyList = new ArrayList<>();

        for (int i = 1; i < 30 ; i++) {
            StoryDTO item = new StoryDTO();
            item.setId(i);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -10 * i);
            item.setDate(new Date(cal.getTimeInMillis()));

            item.setTitle("제목" + i);
            item.setMemo("메모입니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\nMemo : " + i);

            ArrayList<String> imagePathList = new ArrayList<>();
            for (int j = 0 ; j < i % 4 + 1; j ++) {
                imagePathList.add(0, "story_sample" + j);
            }
            item.setImagePathList(imagePathList);
            storyList.add(item);
        }

        return storyList;
    }

}
