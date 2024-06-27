package com.hhplus.lecture.exception;

import com.hhplus.lecture.presentation.dto.ResponseDto;

public class NoUserInfoException extends Exception{

    private ResponseDto response;

    public NoUserInfoException(ResponseDto response) {
        this.response = response;
    }

    public ResponseDto getResponse() {
        return response;
    }
}
