package com.hhplus.lecture.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applyId;
    @NotNull
    private Long scheduleId;
    @NotNull
    private Long userId;
    private LocalDateTime attendDate;
    @NotBlank
    private String attendanceYn;

    public Apply(Long scheduleId, Long userId, LocalDateTime attendDate, String attendanceYn) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.attendDate = attendDate;
        this.attendanceYn = attendanceYn;
    }
}
