package com.tairanchina.teval.service.admin.controller;

import com.tairanchina.teval.service.admin.intercept.Exposed;
import com.tairanchina.teval.service.admin.service.VerifyService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述:
 * 验证码
 *
 * @author hzds
 * @Create 2018-08 : 13 16:55
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    private static Logger logger = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private VerifyService verifyService;

    @ApiOperation(value = "获取验证码", notes = "返回验证码对应标识ID存放于cookie中返回")
    @GetMapping
    @Exposed
    public void getCaptcha(HttpServletResponse response) {
        verifyService.createAndOutputStreamCaptcha(response);
    }
}
