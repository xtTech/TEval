package com.tairanchina.teval.service.admin.controller;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.context.UserStatusContext;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 29 10:02
 */
@RestController
@RequestMapping("/service")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @GetMapping
    public Resp getService(@RequestParam String serviceId){
        Account account = UserStatusContext.instance().getAccount();
        AccountIdentity identity = UserStatusContext.instance().getIdentity();
        return null;
    }

}
