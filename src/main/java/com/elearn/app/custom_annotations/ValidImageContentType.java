package com.elearn.app.custom_annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageContentType.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageContentType {

    String message() default "In Banner , Only png or jpg/jpeg type image are allowed!";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
