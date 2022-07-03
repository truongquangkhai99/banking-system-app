package com.unvise.bankingsystemapp.credit.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreditValidator.class)
@Documented
public @interface ValidateCredit {
    String message() default "Invalid credit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
