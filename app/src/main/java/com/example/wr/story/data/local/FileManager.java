package com.example.wr.story.data.local;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by WR on 2018-01-10.
 */
@Singleton
public class FileManager {

    public FileManager() {}

    public Single<String> savePictureData (String imagePath, final byte[] data) {
        Single<String> savePictureSingle = Single.create(emitter -> {
            File file = new File(imagePath);
            OutputStream os = null;
            try {
                os = new FileOutputStream(file);
                os.write(data);
                os.close();
                emitter.onSuccess(imagePath);
            } catch (IOException e) {
                emitter.onError(e);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
        return savePictureSingle;
    }
}
