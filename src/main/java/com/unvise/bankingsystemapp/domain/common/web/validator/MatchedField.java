package com.unvise.bankingsystemapp.domain.common.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that validates two fields against each other
 * param {@code first} first field name
 * param {@code second} second field name
 *
 * @see MatchedFieldValidator
 * @author unvise
 * @version 0.1
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchedFieldValidator.class)
@Documented
public @interface MatchedField {

    String message() default "The fields must match";

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
