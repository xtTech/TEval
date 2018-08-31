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
 * @Create 2018-08 : 27 16:36
 */
@ApiModel(description = "重置密码邮件发送参数")
public class ResetPwdMailRequestDTO {

    @ApiModelProperty(name = "重置密码邮箱",value = "重置密码邮箱",required = true)
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = Global.RegexString.EMAIL_REGEX,message = "邮箱格式有误")
    private String email;

    @ApiModelProperty(name = "验证码",value = "验证码",required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @ApiModelProperty(name = "验证码唯一标识ID",value = "验证码唯一标识ID",required = true)
    @NotBlank(message = "验证码标记不能为空")
    private String captchaId;

    public String getEmail() {
        return email;
    }

    public ResetPwdMailRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public ResetPwdMailRequestDTO setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        return this;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public ResetPwdMailRequestDTO setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
        return this;
    }
}
