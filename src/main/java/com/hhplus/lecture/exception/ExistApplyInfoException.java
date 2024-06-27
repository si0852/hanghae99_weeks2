package com.hhplus.lecture.exception;

import com.hhplus.lecture.presentation.dto.ResponseDto;

public class ExistApplyInfoException extends Exception{

    private ResponseDto response;

    public ExistApplyInfoException(ResponseDto response) {
        this.response = response;
    }

    public ResponseDto getResponse() {
        return response;
    }
}
