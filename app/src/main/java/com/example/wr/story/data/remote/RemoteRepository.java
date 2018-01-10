package com.example.wr.story.data.remote;

import android.support.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;

import static com.example.wr.story.data.remote.ServiceError.ERROR_UNDEFINED;
import static com.example.wr.story.data.remote.ServiceError.NETWORK_ERROR;

/**
 * Created by WR.
 */

public class RemoteRepository {

    private ServiceGenerator serviceGenerator;

    @Inject
    RemoteRepository(ServiceGenerator serviceGenerator) {
        this.serviceGenerator = serviceGenerator;
    }

//    public Observable<StoryDTO> getSampleDto() {
//        Observable<StoryDTO> sampleDTOObservable = Observable.create(emitter -> {
//            try {
//                SampleApiService sampleApiService = serviceGenerator.createService(SampleApiService.class, BaseUrl.BASE_URL);
//                ServiceResponse serviceResponse = processCall(sampleApiService.getSampleDetailData(), false);
//                if (serviceResponse.getCode() == SUCCESS_CODE) {
//                    StoryDTO sampleModel = (StoryDTO) serviceResponse.getData();
//                    emitter.onNext(sampleModel);
//                    emitter.onComplete();
//                } else {
//                    Throwable throwable = new NetworkErrorException();
//                    emitter.onError(throwable);
//                }
//            } catch (Exception e) {
//                emitter.onError(e);
//            }
//        });
//        return sampleDTOObservable;
//    }

    @NonNull
    private ServiceResponse processCall(Call call, boolean isVoid) {
        try {
            retrofit2.Response response = call.execute();
            if (response == null) {
                return new ServiceResponse(new ServiceError(NETWORK_ERROR, ERROR_UNDEFINED));
            }
            int responseCode = response.code();
            if (response.isSuccessful()) {
                return new ServiceResponse(responseCode, isVoid ? null : response.body());
            } else {
                ServiceError ServiceError;
                ServiceError = new ServiceError(response.message(), responseCode);
                return new ServiceResponse(ServiceError);
            }
        } catch (IOException e) {
            return new ServiceResponse(new ServiceError(NETWORK_ERROR, ERROR_UNDEFINED));
        }
    }
}
