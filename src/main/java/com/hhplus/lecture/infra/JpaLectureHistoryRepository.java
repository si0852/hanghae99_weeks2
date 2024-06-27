package com.hhplus.lecture.infra;

import com.hhplus.lecture.business.entity.LectureHistory;
import com.hhplus.lecture.business.repository.LectureHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLectureHistoryRepository extends JpaRepository<LectureHistory, Long>, LectureHistoryRepository {
    @Override
    default LectureHistory saveHistory(LectureHistory lectureHistory) {
        return save(lectureHistory);
    }
}
