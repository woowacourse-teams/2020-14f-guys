package com.woowacourse.pelotonbackend.support.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FutureOrPresentBasedUTCValidator.class)
@Retention(RUNTIME)
@Target({FIELD})
public @interface FutureOrPresentBasedUTC {
    String message() default "The current or future time should come based on UTC.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
