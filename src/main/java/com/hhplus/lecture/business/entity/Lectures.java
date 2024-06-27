package com.hhplus.lecture.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lectures {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long lcId;

    @NotBlank
    private String lectureName;

    public Lectures(long lcId, String lectureName) {
        this.lcId = lcId;
        this.lectureName = lectureName;
    }
}
