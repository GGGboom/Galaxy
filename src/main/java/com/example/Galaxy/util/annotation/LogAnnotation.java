package com.example.Galaxy.util.annotation;

import com.example.Galaxy.util.enums.OperationType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    String description() default "";

    OperationType operationType() default OperationType.UNKNOWN;
}
