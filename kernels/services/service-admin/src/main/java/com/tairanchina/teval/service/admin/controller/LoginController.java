package com.tairanchina.teval.service.admin.controller;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.dto.LoginRequestDTO;
import com.tairanchina.teval.service.admin.dto.LoginResponseDTO;
import com.tairanchina.teval.service.admin.intercept.Exposed;
import com.tairanchina.teval.service.admin.service.LoginService;
import com.tairanchina.teval.service.admin.service.VerifyService;
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
 * @Create 2018-08 : 24 15:16
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger loggor = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private VerifyService verifyService;

    @PutMapping
    @Exposed
    public Resp<LoginResponseDTO> emailAndPasswordLogin(@Valid @RequestBody LoginRequestDTO requestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return Global.RespError.ILLEGAL_ARGUMENT.detail(result.getFieldError().getDefaultMessage()).get();
        }
        Resp<Void> validateResp = verifyService.validateCode(requestDTO.getVerifyCode(), requestDTO.getCaptchaId());
        if (!validateResp.ok()) {
            return Resp.error(validateResp);
        }
        return loginService.emailAndPasswordLogin(requestDTO);
    }

}
