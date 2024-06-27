package com.hhplus.lecture.exception;

import com.hhplus.lecture.presentation.dto.ResponseDto;

public class NoLectureInfoException extends Exception{

    private ResponseDto response;

    public NoLectureInfoException(ResponseDto response) {
        this.response = response;
    }

    public ResponseDto getResponse() {
        return response;
    }
}
