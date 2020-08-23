package com.woowacourse.pelotonbackend.common.exception;

import java.time.DayOfWeek;
import java.util.List;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.race.domain.DateDuration;

public class MissionNotCreatedException extends BusinessException {
    public MissionNotCreatedException(final DateDuration raceDuration, final List<DayOfWeek> days) {
        super(ErrorCode.MISSION_NOT_CREATED, String.format("레이스 기간 %s에 %s 요일들이 포함되지 않습니다.",
            raceDuration.toString(), days.toString()));
    }
}
