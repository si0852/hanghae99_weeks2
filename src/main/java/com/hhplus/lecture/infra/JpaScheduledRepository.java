package com.hhplus.lecture.infra;

import com.hhplus.lecture.business.entity.Schedule;
import com.hhplus.lecture.business.entity.Users;
import com.hhplus.lecture.business.repository.ScheduleRepository;
import com.hhplus.lecture.business.repository.UserRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaScheduledRepository extends JpaRepository<Schedule, Long>, ScheduleRepository {

    @Override
    default Schedule saveSchedule(Schedule schedule) {
        return save(schedule);
    }

    @Override
    default Schedule getSchedule(long scheduleId) {
        return findById(scheduleId).orElse(null);
    }

    @Override
    default List<Schedule> getScheduleList() {
        return findAll();
    }

    @Override
    default Schedule update(Schedule schedule) {
        return save(schedule);
    }

    List<Schedule> findAll();
}
