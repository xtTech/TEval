package com.tairanchina.teval.service.admin.controller;


import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.dto.MailRequestDTO;
import com.tairanchina.teval.service.admin.service.EmailService;
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

    @PutMapping
    public Resp<Boolean> sendEmail(@Valid @RequestBody MailRequestDTO requestDTO, BindingResult result){
        if (result.hasErrors()) {
            logger.info("参数有误");
            return Resp.error(new Resp(){{setBody(false);}});
        }
        return emailService.sendEmail(requestDTO);
    }
}
