package com.hhplus.lecture.util;

import com.hhplus.lecture.exception.*;
import com.hhplus.lecture.presentation.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDto> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ResponseDto(500, "Server Error", null));
    }

    @ExceptionHandler(value = FullOfPeopleException.class)
    public ResponseEntity<ResponseDto> fullOfPeopleException(FullOfPeopleException e) {
        return ResponseEntity.status(500).body(e.getResponse());
    }

    @ExceptionHandler(value = NoLectureInfoException.class)
    public ResponseEntity<ResponseDto> noLectureInfoException(NoLectureInfoException e) {
        return ResponseEntity.status(500).body(e.getResponse());
    }

    @ExceptionHandler(value = NoOpenDateException.class)
    public ResponseEntity<ResponseDto> noOpenDateException(NoOpenDateException e) {
        return ResponseEntity.status(500).body(e.getResponse());
    }

    @ExceptionHandler(value = NoScheduleInfoException.class)
    public ResponseEntity<ResponseDto> noScheduleInfoException(NoScheduleInfoException e) {
        return ResponseEntity.status(500).body(e.getResponse());
    }

    @ExceptionHandler(value = ExistApplyInfoException.class)
    public ResponseEntity<ResponseDto> notExistApplyInfoException(ExistApplyInfoException e) {
        return ResponseEntity.status(500).body(e.getResponse());
    }

    @ExceptionHandler(value = NoUserInfoException.class)
    public ResponseEntity<ResponseDto> noUserInfoException(NoUserInfoException e) {
        return ResponseEntity.status(500).body(e.getResponse());
    }
}
