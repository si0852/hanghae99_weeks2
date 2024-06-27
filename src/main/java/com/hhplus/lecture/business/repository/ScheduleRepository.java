package com.hhplus.lecture.business.repository;

import com.hhplus.lecture.business.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    Schedule update(Schedule schedule);

    Schedule getSchedule(long scheduleId);

    List<Schedule> getScheduleList();
}
