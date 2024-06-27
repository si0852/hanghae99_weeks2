package com.hhplus.lecture.business.repository;

import com.hhplus.lecture.business.entity.Apply;


public interface ApplyRepository{

    Apply saveApply(Apply apply);
    Apply getApplyInfo(long scheduledId, long userId);

//    Apply isApplied(long userId);
}
