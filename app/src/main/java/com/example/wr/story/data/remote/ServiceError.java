package com.example.wr.story.data.remote;

import lombok.Getter;

/**
 * Created by WR.
 */

public class ServiceError {
    public static final String NETWORK_ERROR = "Unknown ServiceError";
    public static int ERROR_UNDEFINED = -1;
    private static final int GROUP_200 = 2;
    private static final int GROUP_400 = 4;
    private static final int GROUP_500 = 5;
    private static final int VALUE_100 = 100;
    public static final int SUCCESS_CODE = 200;
    public static final int ERROR_CODE = 400;
    @Getter private String description;
    @Getter private int code;

    public static boolean isSuccess(int responseCode) {
        return responseCode / VALUE_100 == GROUP_200;
    }

    public static boolean isClientError(int errorCode) {
        return errorCode / VALUE_100 == GROUP_400;
    }

    public static boolean isServerError(int errorCode) {
        return errorCode / VALUE_100 == GROUP_500;
    }

    public ServiceError() {
    }

    public ServiceError(String description, int code) {
        this.description = description;
        this.code = code;
    }

}
