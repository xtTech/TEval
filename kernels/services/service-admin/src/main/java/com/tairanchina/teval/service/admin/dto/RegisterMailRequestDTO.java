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
 * @Create 2018-08 : 23 10:36
 */
@ApiModel(description = "激活邮件发送请求参数")
public class RegisterMailRequestDTO {

    @ApiModelProperty(name = "注册邮件",value = "注册邮件",required = true)
    @NotBlank(message = "注册邮箱不能为空")
    @Pattern(regexp = Global.RegexString.EMAIL_REGEX,message = "邮箱格式有误")
    private String email;

    @ApiModelProperty(name = "密码",value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = Global.RegexString.PASSWORD_REGEX,message = "密码格式有误,必须包含6-20位数字、字母或者符号中的两种")
    private String password;

    @ApiModelProperty(name = "验证码",value = "验证码",required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @ApiModelProperty(name = "验证码唯一标识ID",value = "验证码唯一标识ID",required = true)
    @NotBlank(message = "验证码标记不能为空")
    private String captchaId;

    public String getEmail() {
        return email;
    }

    public RegisterMailRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterMailRequestDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public RegisterMailRequestDTO setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        return this;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public RegisterMailRequestDTO setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
        return this;
    }
}
