package com.unvise.bankingsystemapp.transaction.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TransactionValidator.class)
@Documented
public @interface ValidateTransaction {
    String message() default "Invalid transaction";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
