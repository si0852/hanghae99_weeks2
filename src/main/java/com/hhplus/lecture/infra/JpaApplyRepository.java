package com.hhplus.lecture.infra;

import com.hhplus.lecture.business.entity.Apply;
import com.hhplus.lecture.business.repository.ApplyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaApplyRepository extends JpaRepository<Apply, Long>, ApplyRepository {
    @Override
    default Apply saveApply(Apply apply) {
        return save(apply);
    }

    @Override
    default Apply getApplyInfo(long scheduleId, long userId) {
        return findByScheduleIdAndUserId(scheduleId, userId).orElse(null);
    }

    Optional<Apply> findByScheduleIdAndUserId(long scheduleId, long userId);
}
