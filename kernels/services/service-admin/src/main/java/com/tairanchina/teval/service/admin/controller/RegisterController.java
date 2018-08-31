package com.tairanchina.teval.service.admin.controller;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.intercept.Exposed;
import com.tairanchina.teval.service.admin.service.AccountIdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 描述:
 * 注册服务
 *
 * @author hzds
 * @Create 2018-08 : 15 17:48
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private AccountIdentityService identityService;

    @GetMapping("/active")
    @Exposed
    public String activeAccount(@RequestParam String code, @RequestParam String email) {
        Resp<Boolean> activeResp = identityService.activeAccountByEmail(email, code);
        if (!activeResp.ok()) {
            logger.error(activeResp.getMessage());
            return "error";
        }
        return "register_success";
    }

}
