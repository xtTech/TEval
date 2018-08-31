package com.tairanchina.teval.service.admin.controller;


import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.dto.MailRequestDTO;
import com.tairanchina.teval.service.admin.dto.RegisterMailRequestDTO;
import com.tairanchina.teval.service.admin.dto.ResetPwdMailRequestDTO;
import com.tairanchina.teval.service.admin.dto.SendMailResponseDTO;
import com.tairanchina.teval.service.admin.intercept.Exposed;
import com.tairanchina.teval.service.admin.service.EmailService;
import com.tairanchina.teval.service.admin.service.VerifyService;
import io.swagger.annotations.ApiOperation;
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
 * email
 *
 * @author hzds
 * @Create 2018-08 : 08 9:18
 */
@RestController
@RequestMapping("/mail")
public class EmailController {

    private static Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerifyService verifyService;

    @ApiOperation(value = "邮件发送", notes = "邮件发送")
    @PutMapping
    @Exposed
    public Resp<Boolean> sendEmail(@Valid @RequestBody MailRequestDTO requestDTO, BindingResult result) {
        if (result.hasErrors()) {
            logger.info(result.getFieldError().getDefaultMessage());
//            return Resp.error(new Resp() {{
//                setMessage(result.getFieldError().getDefaultMessage());
//                setCode("400");
//                setBody(false);
//            }});
            return Global.RespError.ILLEGAL_ARGUMENT.detail(result.getFieldError().getDefaultMessage()).get();
        }
        return emailService.sendEmail(requestDTO);
    }

    @PutMapping("/register")
    public Resp<SendMailResponseDTO> registerMail(@Valid @RequestBody RegisterMailRequestDTO requestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return Global.RespError.ILLEGAL_ARGUMENT.detail(result.getFieldError().getDefaultMessage()).get();
        }
        //验证码验证
        Resp<Void> voidResp = verifyService.validateCode(requestDTO.getVerifyCode(), requestDTO.getCaptchaId());
        if (!voidResp.ok()) {
            return Resp.error(voidResp);
        }
        return emailService.sendRegisterMail(requestDTO);
    }

    @PutMapping("/reset_password")
    @Exposed
    public Resp<SendMailResponseDTO> resetPassword(@Valid @RequestBody ResetPwdMailRequestDTO requestDTO, BindingResult result) {
        if(result.hasErrors()){
            return Global.RespError.ILLEGAL_ARGUMENT.detail(result.getFieldError().getDefaultMessage()).get();
        }
        //验证码验证
        Resp<Void> voidResp = verifyService.validateCode(requestDTO.getVerifyCode(), requestDTO.getCaptchaId());
        if (!voidResp.ok()) {
            return Resp.error(voidResp);
        }
        return emailService.sendResetPwdMail(requestDTO);
    }
}
