package com.example.wr.story.data.local.dao;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by WR on 2018-01-10.
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@RealmClass
public class StoryDAO extends RealmObject {
    @PrimaryKey
    private long id;
    private Date date;
    private String title;
    private String memo;
    private RealmList<RealmString> imagePathList;

}
