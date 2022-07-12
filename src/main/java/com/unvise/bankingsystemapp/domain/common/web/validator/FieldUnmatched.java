package com.unvise.bankingsystemapp.domain.common.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * An annotation that validates the two fields for inconsistency.
 * param {@code first} first field name
 * param {@code second} second field name
 *
 * @see FieldUnmatchedValidator
 * @author unvise
 * @version 0.1
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldUnmatchedValidator.class)
@Documented
public @interface FieldUnmatched {
    String message() default "The fields mustn't match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FieldUnmatched[] value();
    }

}