package com.hhplus.lecture.exception;

import com.hhplus.lecture.presentation.dto.ResponseDto;

public class NoOpenDateException extends Exception{

    private ResponseDto response;

    public NoOpenDateException(ResponseDto response) {
        this.response = response;
    }

    public ResponseDto getResponse() {
        return response;
    }
}
