package com.hhplus.lecture.business.service.impl;

import com.hhplus.lecture.business.entity.*;
import com.hhplus.lecture.business.repository.*;
import com.hhplus.lecture.business.service.ApplyService;
import com.hhplus.lecture.presentation.dto.ApplyDto;
import com.hhplus.lecture.exception.*;
import com.hhplus.lecture.presentation.dto.ResponseDto;
import com.hhplus.lecture.type.LectureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {
    private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);

    private final ApplyRepository applyRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final LectureHistoryRepository lectureHistoryRepository;
    private final ScheduleRepository scheduleRepository;

    public ApplyServiceImpl(ApplyRepository applyRepository, LectureRepository lectureRepository, UserRepository userRepository, LectureHistoryRepository lectureHistoryRepository, ScheduleRepository scheduleRepository) {
        this.applyRepository = applyRepository;
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
        this.lectureHistoryRepository = lectureHistoryRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    @Override
    public Boolean apply(ApplyDto applyDto) throws Exception{
        LocalDateTime today = LocalDateTime.now();
        try {
            Users userInfo = userRepository.getUser(applyDto.getUserId());
            if (userInfo == null) throw new NoUserInfoException(new ResponseDto(500, "유저정보가 없습니다.", false));

            Schedule scheduleInfo = scheduleRepository.getSchedule(applyDto.getScheduleId());
            if (scheduleInfo == null) throw new NoScheduleInfoException(new ResponseDto(500, "등록된 스케쥴이 없습니다.", false));
            if (!scheduleInfo.isMaxAttendees()) throw new FullOfPeopleException(new ResponseDto(500, "인원이 가득찼습니다.", false));


            if (scheduleInfo.isOpenDate(today)) throw new NoOpenDateException(new ResponseDto(500, "오픈일자가 아닙니다.", false));

            Lectures lectureInfo = lectureRepository.getLectures(scheduleInfo.getLcId());
            if (lectureInfo == null) throw new NoLectureInfoException(new ResponseDto(500, "등록된 강의가 없습니다.", false));

            Apply applyInfo = applyRepository.getApplyInfo(applyDto.getScheduleId(), applyDto.getUserId());
            if (applyInfo != null && applyInfo.getAttendanceYn().equals("Y"))
                throw new ExistApplyInfoException(new ResponseDto(500, "신청정보가 존재합니다.", false));

            Apply newApplyInfo = new Apply(applyDto.getScheduleId(), applyDto.getUserId(), today, "Y");
            applyRepository.saveApply(newApplyInfo);

            scheduleInfo.increase();
            scheduleRepository.update(scheduleInfo);

            return true;
        } finally {
            lectureHistoryRepository.saveHistory(new LectureHistory(applyDto.getScheduleId(), applyDto.getUserId(), LectureType.APPLICATION, today));
        }
    }

    @Override
    public Boolean isApplied(long scheduleId, long userId) throws Exception{
        Users userInfo = userRepository.getUser(userId);
        if (userInfo == null) {
            throw new NoUserInfoException(new ResponseDto(500, "유저정보가 없습니다.", null));
        }
        Schedule scheduleInfo = scheduleRepository.getSchedule(scheduleId);
        if (scheduleInfo == null) {
            throw new NoScheduleInfoException(new ResponseDto(500, "등록된 스케쥴이 없습니다.", null));
        }
        Apply applyInfo = applyRepository.getApplyInfo(scheduleId, userId);
        return (applyInfo != null && applyInfo.getAttendanceYn().equals("Y")) ? true : false;
    }

    @Override
    public List<Schedule> getScheduledList() throws Exception {
        List<Schedule> scheduleList = scheduleRepository.getScheduleList();
        if (scheduleList.size() == 0) {
            throw new NoScheduleInfoException(new ResponseDto(500, "등록된 스케쥴이 없습니다.", null));
        }
        return scheduleList;
    }


}
