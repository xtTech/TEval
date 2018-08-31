package com.tairanchina.teval.service.admin.controller;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.dto.ResetPwdRequestDTO;
import com.tairanchina.teval.service.admin.intercept.Exposed;
import com.tairanchina.teval.service.admin.service.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 27 16:30
 */
@RestController
@RequestMapping("/password")
public class PasswordController {

    private final static Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private PasswordService passwordService;

    @PutMapping
    @Exposed
    public Resp<Boolean> resetPassword(@Valid @RequestBody ResetPwdRequestDTO requestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return Global.RespError.ILLEGAL_ARGUMENT.detail(result.getFieldError().getDefaultMessage()).get();
        }
        return passwordService.resetPassword(requestDTO);
    }
}
