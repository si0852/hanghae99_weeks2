package com.hhplus.lecture.business.entity;

import com.hhplus.lecture.type.LectureType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LectureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long historyId;

    @NotNull
    private Long scheduleId;

    @NotNull
    private Long userId;

    @Enumerated(EnumType.STRING)
    private LectureType type;

    private LocalDateTime attendDate;

    public LectureHistory(Long scheduleId, Long userId, LectureType type, LocalDateTime attendDate) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.type = type;
        this.attendDate = attendDate;
    }
}
