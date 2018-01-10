package com.example.wr.story.interactor;

import com.example.wr.story.data.DataRepository;
import com.example.wr.story.interactor.base.SingleUseCase;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by WR on 2018-01-10.
 */

public class SavePicture extends SingleUseCase<String, SavePicture.Params> {

    @Inject
    protected SavePicture(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    protected Single<String> buildUseCaseSingle(Params params) {
        return dataRepository.savePictureData(params.imagePath, params.data);
    }


    public static final class Params {
        private final String imagePath;
        private final byte[] data;

        private Params(String imagePath, byte[] data) {
            this.imagePath = imagePath;
            this.data = data;
        }

        public static Params makeParams(String imagePath, byte[] data) {
            return new Params(imagePath, data);
        }
    }
}
