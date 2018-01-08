package com.example.wr.story.data.remote;

import lombok.Getter;

/**
 * Created by WR.
 */

public class ServiceResponse {
    @Getter private int code;
    @Getter private Object data;
    @Getter private ServiceError ServiceError;

    public ServiceResponse(int code, Object response) {
        this.code = code;
        this.data = response;
    }

    public ServiceResponse(ServiceError ServiceError) {
        this.ServiceError = ServiceError;
    }

    public ServiceResponse(Object response) {
        this.data = response;
    }

}
