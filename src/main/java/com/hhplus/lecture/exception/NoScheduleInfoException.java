package com.hhplus.lecture.exception;

import com.hhplus.lecture.presentation.dto.ResponseDto;

public class NoScheduleInfoException extends Exception{

    private ResponseDto response;

    public NoScheduleInfoException(ResponseDto response) {
        this.response = response;
    }

    public ResponseDto getResponse() {
        return response;
    }
}
