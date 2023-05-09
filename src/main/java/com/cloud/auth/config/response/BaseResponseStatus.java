package com.cloud.auth.config.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true,1000, "요청에 성공하셨습니다."),

    /**
     * 2000 : Request 오류
     */
    NOT_EXIST_GROUP(false, 2000, "존재하지 않는 그룹입니다."),
    NOT_EXIST_USER_GROUP(false, 2001,"유저가 그룹에 포함 되어있지 않습니다."),

    /**
     * 3000 : Response 오류
     */
    VALIDATED_ERROR(false, 3000, "VALIDATED_ERROR");
    private final boolean isSuccess;
    private final int code;
    private final String message;
    BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
