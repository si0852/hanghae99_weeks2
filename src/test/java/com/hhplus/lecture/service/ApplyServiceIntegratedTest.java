package com.hhplus.lecture.service;


import com.hhplus.lecture.business.entity.Schedule;
import com.hhplus.lecture.business.repository.ApplyRepository;
import com.hhplus.lecture.business.repository.LectureRepository;
import com.hhplus.lecture.business.repository.ScheduleRepository;
import com.hhplus.lecture.business.repository.UserRepository;
import com.hhplus.lecture.business.service.ApplyService;
import com.hhplus.lecture.exception.*;
import com.hhplus.lecture.presentation.dto.ApplyDto;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplyServiceIntegratedTest {

    @Autowired
    ApplyService applyService;

    @Autowired
    ApplyRepository applyRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    UserRepository userRepository;




    @Test
    @DisplayName("수강신청여부 - 유저정보가 없을때")
    @Transactional
    void apply_no_user() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(4L);
        applyDto.setScheduleId(1L);

        // when&then
        assertThrows(NoUserInfoException.class, () -> {
            applyService.apply(applyDto);
        });
    }

    @Test
    @DisplayName("수강신청여부 - 스케쥴정보가 없을때")
    @Transactional
    void apply_no_schedule() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(2L);
        applyDto.setScheduleId(4L);

        // when&then
        assertThrows(NoScheduleInfoException.class, () -> {
            applyService.apply(applyDto);
        });
    }

    @DisplayName("신청정보 - 인원 가득찼을때")
    @Test
    @Transactional
    void apply_full_people() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(2L);
        applyDto.setScheduleId(1L);

        // when&then
        assertThrows(FullOfPeopleException.class, () -> {
            applyService.apply(applyDto);
        });
    }

    @DisplayName("신청정보 - 오픈 일자 전일때")
    @Test
    @Transactional
    void apply_before_openDate() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(2L);
        applyDto.setScheduleId(2L);

        // when&then
        assertThrows(NoOpenDateException.class, () -> {
            applyService.apply(applyDto);
        });
    }

    @DisplayName("신청정보 - 강의 정보 없을때")
    @Test
    @Transactional
    void apply_no_lecture() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(2L);
        applyDto.setScheduleId(4L);

        // when&then
        assertThrows(NoLectureInfoException.class, () -> {
            applyService.apply(applyDto);
        });
    }

    @DisplayName("신청정보 - 신청 정보 있을때")
    @Test
    @Transactional
    void apply_exist() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(2L);
        applyDto.setScheduleId(5L);

        // when&then
        assertThrows(ExistApplyInfoException.class, () -> {
            applyService.apply(applyDto);
        });
    }

    @DisplayName("신청정보 등록")
    @Test
    @Transactional
    void apply_info() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(3L);
        applyDto.setScheduleId(6L);

        // when
        Boolean apply = applyService.apply(applyDto);

        //then
        Assertions.assertThat(apply).isTrue();

    }

    @DisplayName("신청여부확인")
    @Test
    @Transactional
    void is_applied() throws Exception{
        //given
        ApplyDto applyDto = new ApplyDto();
        applyDto.setUserId(1L);
        applyDto.setScheduleId(1L);

        //when
        Boolean applied = applyService.isApplied(applyDto.getScheduleId(), applyDto.getScheduleId());

        //then
        assertEquals(applied, true);
    }

    @DisplayName("특강목록조회")
    @Test
    @Transactional
    void get_schedule_list() throws Exception{
        //given
        //when
        List<Schedule> scheduledList = applyService.getScheduledList();

        //then
        assertEquals(scheduledList.size(), 6);
    }


}
