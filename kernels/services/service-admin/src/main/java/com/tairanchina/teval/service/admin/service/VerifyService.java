package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;

import javax.servlet.http.HttpServletResponse;

/**
 * 描述:
 * 验证相关
 *
 * @author hzds
 * @Create 2018-08 : 14 11:30
 */
public interface VerifyService {
    /**
     * 返回验证码流
     *
     * @param response
     */
    Resp<Boolean> createAndOutputStreamCaptcha(HttpServletResponse response);

    /**
     * 验证码验证
     * @param code
     * @param captchaId
     * @return
     */
    Resp<Void> validateCode(String code, String captchaId);
}
