package com.hhplus.lecture.business.repository;

import com.hhplus.lecture.business.entity.Lectures;

public interface LectureRepository{

    Lectures saveLectures(Lectures lecture);

    Lectures getLectures(long lcId);
}
