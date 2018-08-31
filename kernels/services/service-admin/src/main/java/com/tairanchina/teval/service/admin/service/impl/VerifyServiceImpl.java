package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.csp.dew.Dew;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.service.VerifyService;
import com.tairanchina.teval.service.admin.util.VerifyCodeUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述:
 * 验证相关实现
 *
 * @author hzds
 * @Create 2018-08 : 14 11:34
 */
@Service
public class VerifyServiceImpl implements VerifyService {


    @Override
    public Resp<Boolean> createAndOutputStreamCaptcha(HttpServletResponse response) {
        String captchaId = $.field.createUUID();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        Cookie cookie = new Cookie(Global.Cookie.CAPTCHA, captchaId);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        String verifyCode = VerifyCodeUtils.generateVerifyCode(Global.CodeParams.CODE_LENGTH);
        Dew.cluster.cache.setex(Global.P_SIMPLE_CAPTCHA_ID + captchaId, verifyCode, Global.CodeParams.CODE_ACTIVE_MINUTES * 60);
        try {
            VerifyCodeUtils.outputImage(Global.CodeParams.CODE_WIDTH, Global.CodeParams.CODE_HEIGHT, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
            return Global.RespError.SYSTEM_ERROR.detail("获取验证码失败").get();
        }
        return Resp.success(true);
    }

    @Override
    public Resp<Void> validateCode(String code, String captchaId) {
        String verifyCode = Dew.cluster.cache.get(Global.P_SIMPLE_CAPTCHA_ID + captchaId);
        if (verifyCode == null || "".equals(verifyCode)) {
            return Global.RespError.EMPTY_VALUE.detail("验证码错误，请重新获取").get();
        }
        if (!code.equalsIgnoreCase(verifyCode)) {
            return Global.RespError.BAD_ARGUMENT.detail("验证码输入错误").get();
        }
        return Resp.success(null);
    }

}
