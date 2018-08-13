package com.tairanchina.teval.service.crypto.com.advice;

import com.ecfront.dew.common.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Resp handle(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterable = constraintViolations.iterator();
        if(iterable.hasNext()){
            ConstraintViolation<?> constraintViolation = iterable.next();
            return Resp.badRequest(constraintViolation.getMessage());
        }
        return Resp.unknown("Unknown error");
    }
}
