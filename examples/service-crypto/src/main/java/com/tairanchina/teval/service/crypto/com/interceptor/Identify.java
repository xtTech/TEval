package com.tairanchina.teval.service.crypto.com.interceptor;

import java.lang.annotation.*;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/11.
 */
@Documented
@Target({
        ElementType.METHOD, ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Identify {
}
