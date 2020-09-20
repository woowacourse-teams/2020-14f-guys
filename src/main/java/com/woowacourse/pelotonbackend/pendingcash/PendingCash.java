package com.woowacourse.pelotonbackend.pendingcash;

import com.woowacourse.pelotonbackend.member.domain.Member;
import com.woowacourse.pelotonbackend.vo.Cash;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@Getter
@Table
public class PendingCash {
    @Id @With(value = AccessLevel.PACKAGE)
    private final Long id;

    private final AggregateReference<Member, @NotNull Long> memberId;

    @Embedded.Empty @Valid
    private final Cash cash;

    private final CashStatus cashStatus;

    @CreatedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime createdAt;

    @LastModifiedDate @PastOrPresent
    @With(AccessLevel.PACKAGE)
    private final LocalDateTime updatedAt;
}
