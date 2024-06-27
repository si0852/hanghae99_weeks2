package com.hhplus.lecture.infra;

import com.hhplus.lecture.business.entity.Lectures;
import com.hhplus.lecture.business.repository.LectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLectureRepository extends JpaRepository<Lectures, Long>, LectureRepository {
    @Override
    default Lectures saveLectures(Lectures lecture) {
        return save(lecture);
    }

    @Override
    default Lectures getLectures(long lcId) {
        return findById(lcId).orElse(null);
    }
}
