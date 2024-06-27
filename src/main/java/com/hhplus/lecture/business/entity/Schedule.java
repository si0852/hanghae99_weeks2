package com.hhplus.lecture.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    @NotNull
    private Long lcId;
    @NotBlank
    private String lectureName;
    private LocalDateTime openDate;
    @Min(1)
    private int maxAttendees;
    private int attendees;

    public Schedule(Long lcId, LocalDateTime openDate,int maxAttendees, int attendees ,String lectureName) {
        this.lcId = lcId;
        this.openDate = openDate;
        this.maxAttendees = maxAttendees;
        this.lectureName = lectureName;
        this.attendees = attendees;
    }

    public boolean isMaxAttendees() {
        if (this.maxAttendees < attendees+1) return false;
        return true;
    }

    public boolean isOpenDate(LocalDateTime applyDate) {
        return applyDate.isBefore(this.openDate);
    }

    public void increase() {
        this.attendees += 1;
    }
}
