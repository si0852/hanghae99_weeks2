package com.hhplus.lecture.service;


import com.hhplus.lecture.business.entity.*;
import com.hhplus.lecture.business.repository.*;
import com.hhplus.lecture.business.service.impl.ApplyServiceImpl;
import com.hhplus.lecture.presentation.dto.ApplyDto;
import com.hhplus.lecture.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplyServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);
    @InjectMocks
    ApplyServiceImpl applyService;

    @Mock
    private ApplyRepository applyRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private LectureHistoryRepository lectureHistoryRepository;

    @Mock
    UserRepository userRepository;

    private  ApplyDto applyDto;

    @BeforeEach
    void set_up() {
        applyDto = new ApplyDto();
        applyDto.setUserId(1L);
        applyDto.setScheduleId(2L);
    }


    // 특강목록 API Test 1) 스케쥴 목록에 없으면 등록된 스케줄 정보가 없습니다 Exception
    // 특상 신청 완료 여부 조회 API 1) user 정보 확인 2) schedule 정보 확인 3) 신청목록에 있는지 확인


    @DisplayName("유저 정보 없을 경우 Exception")
    @Test
    void no_user() throws Exception{
        //given
        when(userRepository.getUser(applyDto.getUserId())).thenReturn(null);

        //when && then
        assertThrows(NoUserInfoException.class, () ->
            applyService.apply(applyDto)
        );
    }

    @DisplayName("스케쥴 정보가 없을 경우 Exception")
    @Test
    void no_schedule() throws Exception{
        //given
        when(userRepository.getUser(applyDto.getUserId())).thenReturn(new Users(2L, "sihyun"));
        when(scheduleRepository.getSchedule(applyDto.getScheduleId())).thenReturn(null);

        //when && then
        assertThrows(NoScheduleInfoException.class, () ->
                applyService.apply(applyDto)
        );
    }

    @DisplayName("스케쥴정보 - 인원이 가득찼을 경우")
    @Test
    void schedule_full_of_people() throws Exception{
        //given
        when(userRepository.getUser(applyDto.getUserId())).thenReturn(new Users(2L, "sihyun"));
        Schedule schedule = new Schedule(3L, getDate(2024, 06,26, 13, 10), 30, 30, "한국사");
        when(scheduleRepository.getSchedule(applyDto.getScheduleId())).thenReturn(schedule);

        //when && then
        assertThrows(FullOfPeopleException.class, () ->
                applyService.apply(applyDto)
        );
    }

    @DisplayName("스케쥴정보 - 오픈전에 신청할 경우")
    @Test
    void schedule_before_openDate() throws Exception{
        //given
        when(userRepository.getUser(applyDto.getUserId())).thenReturn(new Users(2L, "sihyun"));
        Schedule schedule = new Schedule(3L, getDate(2024, 07,27, 13, 10), 30, 0,"한국사");
        when(scheduleRepository.getSchedule(applyDto.getScheduleId())).thenReturn(schedule);

        //when && then
        assertThrows(NoOpenDateException.class, () ->
                applyService.apply(applyDto)
        );
    }

    @DisplayName("강의정보 - 강의 정보가 없을 경우")
    @Test
    void no_lecture() throws Exception{
        //given
        when(userRepository.getUser(applyDto.getUserId())).thenReturn(new Users(2L, "sihyun"));
        log.info("openDate: " + getDate(2024, 06,27, 10, 00));
        log.info("applyDate: " + new Date());
        Schedule schedule = new Schedule(3L, getDate(2024, 06,27, 10, 00), 30, 0,"한국사");
        when(scheduleRepository.getSchedule(applyDto.getScheduleId())).thenReturn(schedule);
        when(lectureRepository.getLectures(3L)).thenReturn(null);

        //when && then
        assertThrows(NoLectureInfoException.class, () ->
                applyService.apply(applyDto)
        );
    }

    @DisplayName("신청정보 - 신청정보가 not null 일경우")
    @Test
    void apply_not_null() throws Exception{
        //given
        ApplyDto applyDtoN = new ApplyDto();
        applyDtoN.setUserId(2L);
        applyDtoN.setScheduleId(3L);
        when(userRepository.getUser(applyDtoN.getUserId())).thenReturn(new Users(2L, "sihyun"));
        Schedule schedule = new Schedule( 3L, getDate(2024, 06,25, 10, 00), 30, 0,"한국사");
        when(scheduleRepository.getSchedule(applyDtoN.getScheduleId())).thenReturn(schedule);
        when(lectureRepository.getLectures(3L)).thenReturn(new Lectures(3L, "한국사"));
        when(applyRepository.getApplyInfo(applyDtoN.getScheduleId(), applyDtoN.getUserId())).
                thenReturn(new Apply(applyDtoN.getScheduleId(), applyDtoN.getUserId(), LocalDateTime.now(), "Y"));

        //when && then
        assertThrows(ExistApplyInfoException.class, () ->
                applyService.apply(applyDtoN)
        );
    }

    @DisplayName("신청정보 - 신청정보 참석여부가 N 일경우")
    @Test
    void apply_attendanceYn() throws Exception{
        //given
        ApplyDto applyDtoN = new ApplyDto();
        applyDtoN.setUserId(2L);
        applyDtoN.setScheduleId(3L);
        when(userRepository.getUser(applyDtoN.getUserId())).thenReturn(new Users(2L, "sihyun"));
        Schedule schedule = new Schedule(3L, getDate(2024, 06,27, 10, 00), 30, 0,"한국사");
        when(scheduleRepository.getSchedule(applyDtoN.getScheduleId())).thenReturn(schedule);
        when(lectureRepository.getLectures(3L)).thenReturn(new Lectures(3L, "한국사"));
        Apply getApply = new Apply(1L, 2L, LocalDateTime.now(), "N");
        when(applyRepository.getApplyInfo(applyDtoN.getScheduleId(), applyDtoN.getUserId())).thenReturn(getApply);

        //when
        Boolean apply = applyService.apply(applyDtoN);
        // then
        assertThat(apply).isTrue();
    }

    @DisplayName("신청정보 - save 정상 작동여부")
    @Test
    void apply_working() throws Exception{
        //given
        long userId = 2L;
        long scheduleId = 1L;
        long lcId = 3L;
        ApplyDto applyDto2 = new ApplyDto();
        applyDto2.setScheduleId(scheduleId);
        applyDto2.setUserId(userId);
        when(userRepository.getUser(applyDto2.getUserId())).thenReturn(new Users(userId, "sihyun"));
        Schedule schedule = new Schedule(lcId, getDate(2024, 06,27, 10, 00), 30, 0,"한국사");
        when(scheduleRepository.getSchedule(applyDto2.getScheduleId())).thenReturn(schedule);
        when(lectureRepository.getLectures(lcId)).thenReturn(new Lectures(lcId, "한국사"));
        Apply getApply = new Apply(scheduleId, userId, LocalDateTime.now(), "N");
        when(applyRepository.getApplyInfo(scheduleId, userId)).thenReturn(getApply);
        //when
        Boolean savedApply = applyService.apply(applyDto2);
        // then
        assertThat(savedApply).isTrue();



//        verify(userRepository, times(1)).getUser(userId);
//        verify(scheduleRepository, times(1)).getSchedule(scheduleId);
//        verify(scheduleRepository, times(1)).saveSchedule(schedule);
//        verify(lectureRepository, times(1)).findById(lcId);
//        verify(applyRepository, times(1)).getApplyInfo(scheduleId, userId);
//        verify(applyRepository, times(1)).saveApply(getApply);
//        verify(lectureHistoryRepository, times(1)).saveHistory(any());
    }

    @DisplayName("수강신청여부 - 유저정보 없을 경우")
    @Test
    void isApplied_no_user() {
        //given
        long scheduleId = 1L;
        long userId = 2L;
        when(userRepository.getUser(userId)).thenReturn(null);
        //when & then
        assertThrows(NoUserInfoException.class, () -> {
            applyService.isApplied(scheduleId, userId);
        });
    }

    @DisplayName("수강신청여부 - 강의정보 없을 경우")
    @Test
    void isApplied_no_lecture() {
        //given
        long scheduleId = 1L;
        long userId = 2L;
        when(userRepository.getUser(userId)).thenReturn(new Users(userId, "sihyun"));
        when(scheduleRepository.getSchedule(scheduleId)).thenReturn(null);
        //when & then
        assertThrows(NoScheduleInfoException.class, () -> {
            applyService.isApplied(scheduleId, userId);
        });
    }

    @DisplayName("수강신청여부 - 수강정보가 없을 경우(null)")
    @Test
    void isApplied_no_apply() throws Exception{
        //given
        long scheduleId = 1L;
        long userId = 2L;
        long lcId = 3L;
        LocalDateTime openDate = LocalDateTime.now();
        when(userRepository.getUser(userId)).thenReturn(new Users(userId, "sihyun"));
        when(scheduleRepository.getSchedule(scheduleId)).thenReturn(new Schedule(lcId, openDate,30 , 0,"tdd"));
        when(applyRepository.getApplyInfo(scheduleId, userId)).thenReturn(null);
        //when
        Boolean applied = applyService.isApplied(scheduleId, userId);
        // then
        assertThat(applied).isFalse();
    }

    @DisplayName("수강신청여부 - 수강정보가 없을 경우(attendanceYn이 N)")
    @Test
    void isApplied_no_apply2() throws Exception{
        //given
        long scheduleId = 1L;
        long userId = 2L;
        long lcId = 3L;
        LocalDateTime openDate = LocalDateTime.now();
        String attendanceYn = "N";
        when(userRepository.getUser(userId)).thenReturn(new Users(userId, "sihyun"));
        when(scheduleRepository.getSchedule(scheduleId)).thenReturn(new Schedule(lcId, openDate,30 , 0,"tdd"));
        when(applyRepository.getApplyInfo(scheduleId, userId)).thenReturn(new Apply(scheduleId, userId, openDate,attendanceYn));
        //when
        Boolean applied = applyService.isApplied(scheduleId, userId);
        // then
        assertThat(applied).isFalse();
    }

    @DisplayName("수강신청여부 - 수강정보가 있을 경우")
    @Test
    void isApplied_exist_apply() throws Exception{
        //given
        long scheduleId = 1L;
        long userId = 2L;
        long lcId = 3L;
        LocalDateTime openDate = LocalDateTime.now();
        String attendanceYn = "Y";
        when(userRepository.getUser(userId)).thenReturn(new Users(userId, "sihyun"));
        when(scheduleRepository.getSchedule(scheduleId)).thenReturn(new Schedule(lcId, openDate,30 ,0,"tdd"));
        when(applyRepository.getApplyInfo(scheduleId, userId)).thenReturn(new Apply(scheduleId, userId, openDate,attendanceYn));
        //when
        Boolean applied = applyService.isApplied(scheduleId, userId);
        // then
        assertThat(applied).isTrue();
    }

    @DisplayName("특강목록조회 - 없는경우")
    @Test
    void schedule_no_exist() throws Exception{
        //given
        List<Schedule> scheduleList = List.of();
        when(scheduleRepository.getScheduleList()).thenReturn(scheduleList);

        //when && then
        assertThrows(NoScheduleInfoException.class,()->{
            applyService.getScheduledList();
        });
    }

    private LocalDateTime getDate(int year, int month, int date, int hour, int minute) {
        return LocalDateTime.of(year, month, date, hour, minute, 00);
    }

}
