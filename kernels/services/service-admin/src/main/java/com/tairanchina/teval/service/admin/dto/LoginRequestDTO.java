package com.tairanchina.teval.service.admin.dto;

import com.tairanchina.teval.common.constant.Global;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 15:34
 */
@ApiModel(value = "用户登录", description = "用户邮箱登录请求信息")
public class LoginRequestDTO {

    @ApiModelProperty(name = "登陆账号", notes = "用户注册邮箱账号", required = true)
    @NotBlank(message = "登录账号不能为空")
    @Pattern(regexp = Global.RegexString.EMAIL_REGEX,message = "邮箱格式有误")
    private String email;

    @ApiModelProperty(name = "登陆密码", notes = "用户登录密码", required = true)
    @NotBlank(message = "用户登录密码不能为空")
    private String password;

    @ApiModelProperty(name = "验证码", notes = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @ApiModelProperty(name = "验证码标识", notes = "验证码标识", required = true)
    @NotBlank(message = "验证码标识不能为空")
    private String captchaId;

    @ApiModelProperty(name = "用户免登陆时长(单位: 分钟)", notes = "默认7天免登陆")
    private Long rememberMeMinutes = 7 * 24 * 60L;

    public String getEmail() {
        return email;
    }

    public LoginRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequestDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public LoginRequestDTO setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        return this;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public LoginRequestDTO setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
        return this;
    }

    public Long getRememberMeMinutes() {
        return rememberMeMinutes;
    }

    public LoginRequestDTO setRememberMeMinutes(Long rememberMeMinutes) {
        this.rememberMeMinutes = rememberMeMinutes;
        return this;
    }
}
