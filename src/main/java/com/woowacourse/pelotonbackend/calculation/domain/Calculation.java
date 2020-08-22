package com.woowacourse.pelotonbackend.calculation.domain;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;

import com.woowacourse.pelotonbackend.common.exception.DuplicateCalculationException;
import com.woowacourse.pelotonbackend.race.domain.Race;
import com.woowacourse.pelotonbackend.rider.domain.Rider;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.With;

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
public class Calculation {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    private final AggregateReference<Rider, @NonNull Long> riderId;

    private final AggregateReference<Race, @NotNull Long> raceId;

    private boolean isCalculated;

    @Embedded.Empty @Valid
    private final Cash prize;

    @CreatedDate @PastOrPresent
    @With(value = AccessLevel.PACKAGE)
    private final LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    @With(value = AccessLevel.PACKAGE)
    private final LocalDateTime updatedAt;

    public Calculation receivePrize() {
        if(this.isCalculated) {
            throw new DuplicateCalculationException(raceId.getId(), riderId.getId());
        }

        return this.toBuilder()
            .isCalculated(true)
            .build();
    }
}
