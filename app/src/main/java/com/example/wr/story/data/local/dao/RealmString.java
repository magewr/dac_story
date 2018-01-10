package com.example.wr.story.data.local.dao;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by WR on 2018-01-10.
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
@RealmClass
public class RealmString extends RealmObject {
    private String value;

    public RealmString() {}

    public RealmString(String string) {
        value = string;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
