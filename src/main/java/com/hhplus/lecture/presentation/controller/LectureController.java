package com.hhplus.lecture.presentation.controller;

import com.hhplus.lecture.business.entity.Schedule;
import com.hhplus.lecture.business.service.ApplyService;
import com.hhplus.lecture.presentation.dto.ApplyDto;
import com.hhplus.lecture.presentation.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecture")
public class LectureController {

    @Autowired
    ApplyService applyService;

    @PostMapping("/apply")
    public ResponseEntity<ResponseDto> apply(@RequestBody ApplyDto applyDto) throws Exception  {
        Boolean apply = applyService.apply(applyDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpServletResponse.SC_OK, apply ? "Success" : "Fail", apply));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> getSchedule() throws Exception  {
        List<Schedule> scheduledList = applyService.getScheduledList();
        return ResponseEntity.ok().body(new ResponseDto(HttpServletResponse.SC_OK, "Success", scheduledList));
    }

    @GetMapping("/application/{userId}/{scheduleId}")
    public ResponseEntity<ResponseDto> isApplied(
            @PathVariable(name = "userId") long userId,
            @PathVariable(name = "scheduleId") long scheduleId
    ) throws Exception  {
        Boolean applied = applyService.isApplied(scheduleId, userId);
        return ResponseEntity.ok().body(new ResponseDto(HttpServletResponse.SC_OK, "Success", applied));
    }
}
