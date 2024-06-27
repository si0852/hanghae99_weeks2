package com.hhplus.lecture.business.service;

import com.hhplus.lecture.business.entity.Schedule;
import com.hhplus.lecture.presentation.dto.ApplyDto;

import java.util.List;

public interface ApplyService {
    Boolean apply(ApplyDto applyDto) throws Exception;

    Boolean isApplied(long scheduleId, long userId) throws Exception;

    List<Schedule> getScheduledList() throws Exception;
}
