package com.woowacourse.pelotonbackend.support.annotation;

import static com.woowacourse.pelotonbackend.support.annotation.FutureOrPresentBasedUTC.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FutureOrPresentBasedUTCValidator.class)
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, TYPE_USE})
public @interface FutureOrPresentBasedUTC {
    String message() default "The current or future time should come based on UTC.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
