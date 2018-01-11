package com.example.wr.story.data.local;

import io.reactivex.Single;

/**
 * Created by WR on 2018-01-11.
 */

public interface FileManager {
    Single<String> savePictureData (String imagePath, final byte[] data);
}
