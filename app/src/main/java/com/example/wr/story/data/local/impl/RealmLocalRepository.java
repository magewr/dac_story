package com.example.wr.story.data.local.impl;

import com.example.wr.story.data.local.LocalRepository;
import com.example.wr.story.data.local.dao.RealmString;
import com.example.wr.story.data.local.dao.StoryDAO;
import com.example.wr.story.data.local.dto.StoryDTO;
import com.example.wr.story.data.local.mapper.StoryMapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by WR.
 */
@Singleton
public class RealmLocalRepository implements LocalRepository {

    private BehaviorSubject<List<StoryDTO>> itemListBehaviorSubject;
    private RealmResults<StoryDAO> results;

    @Override
    public Observable<List<StoryDTO>> getStoryDTOList() {
        if (itemListBehaviorSubject == null)
            itemListBehaviorSubject = BehaviorSubject.createDefault(getSortedStoryList());
        return itemListBehaviorSubject;
    }

    private List<StoryDTO> getSortedStoryList() {
        results = realm().where(StoryDAO.class).findAllSorted("date", Sort.DESCENDING);
        results.addChangeListener((storyDAOS, changeSet) ->
            itemListBehaviorSubject.onNext(StoryMapper.convertList(storyDAOS))
        );
        ArrayList<StoryDTO> storyList = new ArrayList<>();
        storyList.addAll(StoryMapper.convertList(results));
        return storyList;
    }

    @Override
    public Single<StoryDTO> getStoryDTOById(long id) {
        Single<StoryDTO> sampleStoryDTOListObservable = Single.create(emitter -> {
            try {
                StoryDAO results = realm()
                        .where(StoryDAO.class)
                        .equalTo("id", id)
                        .findFirst();
                emitter.onSuccess(StoryMapper.convertItem(results));
            }catch (Exception e) {
                emitter.onError(e);
            }
        });
        return sampleStoryDTOListObservable;
    }

    @Override
    public Single<List<StoryDTO>> getStoryListByString(String string) {
        Single<List<StoryDTO>> sampleStoryDTOListSingle = Single.create(emitter -> {
            try {
                RealmResults<StoryDAO> results = realm()
                        .where(StoryDAO.class)
                        .contains("title", string)
                        .or()
                        .contains("memo", string)
                        .findAllSorted("date", Sort.DESCENDING);
                emitter.onSuccess(StoryMapper.convertList(results));
            }catch (Exception e) {
                emitter.onError(e);
            }
        });
        return sampleStoryDTOListSingle;
    }

    @Override
    public Completable updateStoryDTO(StoryDTO newItem) {
        Completable completable = Completable.create(emitter -> {
            try {
                realm().executeTransaction(realm1 -> {
                    StoryDAO item = realm1
                            .where(StoryDAO.class)
                            .equalTo("id", newItem.getId())
                            .findFirst();
                    StoryMapper.copyItem(newItem, item);
                    realm1.insertOrUpdate(item);
                    emitter.onComplete();
                });
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        });

        return completable;
    }

    @Override
    public Completable addStoryDTO(StoryDTO newItem) {
        Completable completable = Completable.create(emitter -> {
            try {
                realm().beginTransaction();
                StoryDAO dao = realm().createObject(StoryDAO.class, newItem.getId());
                StoryMapper.copyItem(newItem, dao);
                realm().commitTransaction();
                emitter.onComplete();
            }
            catch (Exception e) {
                realm().close();
                emitter.onError(e);
            }
        });

        return completable;
    }

    @Override
    public Completable removeStoryDTO(StoryDTO targetItem) {
        Completable completable = Completable.create(emitter -> {
            try {
                realm().executeTransaction(realm1 -> {
                    StoryDAO item = realm1
                            .where(StoryDAO.class)
                            .equalTo("id", targetItem.getId())
                            .findFirst();
                    item.deleteFromRealm();
                });
                emitter.onComplete();
            }
            catch (Exception e) {
                emitter.onError(e);
            }
        });
        return completable;
    }
    
    private Realm realm() {
        return Realm.getDefaultInstance();
    } 


    //////////////////////////////////////////////////////
    //    샘플 데이터 제작용 메소드
    //
    @Override
    public Observable<List<StoryDTO>> getSampleStoryDTOList() {
        makeSampleStoryDAOList();
        return getStoryDTOList();
    }

    private void makeSampleStoryDAOList() {

        for (int i = 1; i < 30 ; i++) {
            StoryDAO item = new StoryDAO();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -10 * i);
            item.setDate(new Date(cal.getTimeInMillis()));
            item.setId(item.getDate().getTime());

            item.setTitle("제목" + i);
            item.setMemo("메모입니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\n메모는 멀티라인이 가능합니다.\nMemo : " + i);

            RealmList<RealmString> imagePathList = new RealmList<>();
            for (int j = 0 ; j < i % 4 + 1; j ++) {
                imagePathList.add(0, new RealmString("story_sample" + j));
            }
            item.setImagePathList(imagePathList);
            realm().beginTransaction();
            realm().copyToRealm(item);
            realm().commitTransaction();
        }


    }

}
