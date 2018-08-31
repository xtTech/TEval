package com.tairanchina.teval.service.admin.intercept;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exposed {
    String Type() default "";
}
