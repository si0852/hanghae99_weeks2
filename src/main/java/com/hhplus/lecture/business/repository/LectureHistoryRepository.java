package com.hhplus.lecture.business.repository;

import com.hhplus.lecture.business.entity.LectureHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureHistoryRepository{
    LectureHistory saveHistory(LectureHistory lectureHistory);
}
