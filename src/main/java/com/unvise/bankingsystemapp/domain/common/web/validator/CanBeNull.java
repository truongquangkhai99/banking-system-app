package com.unvise.bankingsystemapp.domain.common.web.validator;

import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The annotation, which serves as a marker that a field in a Dto class can,
 * under certain circumstances, be either null or not null
 *
 * @author unvise
 * @version 0.1
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CanBeNull {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
