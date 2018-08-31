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
 * @Create 2018-08 : 27 17:24
 */
@ApiModel(description = "密码重置接口")
public class ResetPwdRequestDTO {

    @ApiModelProperty(name = "重置密码邮箱账号", notes = "重置密码邮箱账号", required = true)
    @Pattern(regexp = Global.RegexString.EMAIL_REGEX, message = "邮箱格式有误")
    private String email;

    @ApiModelProperty(name = "新密码", value = "重置密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String newPassword;

    @ApiModelProperty(name = "系统唯一标识码", notes = "系统唯一标识码", required = true)
    @NotBlank(message = "标识码不能为空")
    private String code;

    public String getEmail() {
        return email;
    }

    public ResetPwdRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ResetPwdRequestDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResetPwdRequestDTO setCode(String code) {
        this.code = code;
        return this;
    }
}
