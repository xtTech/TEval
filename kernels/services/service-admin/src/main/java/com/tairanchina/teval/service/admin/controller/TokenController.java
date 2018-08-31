package com.tairanchina.teval.service.admin.controller;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.dto.ValidateTokenRequestDTO;
import com.tairanchina.teval.service.admin.dto.ValidateTokenResponseDTO;
import com.tairanchina.teval.service.admin.service.AccountTokenService;
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
 * @Create 2018-08 : 27 9:28
 */
@RequestMapping("/token")
@RestController
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private AccountTokenService tokenService;

    @PutMapping
    public Resp<ValidateTokenResponseDTO> validateToken(@Valid @RequestBody ValidateTokenRequestDTO requestDTO, BindingResult result) {
        if (result.hasFieldErrors()) {
            return Global.RespError.ILLEGAL_ARGUMENT.detail(result.getFieldError().getDefaultMessage()).get();
        }
        Resp<ValidateTokenResponseDTO> responseDTOResp = tokenService.validateToken(requestDTO.getToken());
        if (!responseDTOResp.ok()) {
            return Resp.error(responseDTOResp);
        }
        return Resp.success(responseDTOResp.getBody());
    }

}
