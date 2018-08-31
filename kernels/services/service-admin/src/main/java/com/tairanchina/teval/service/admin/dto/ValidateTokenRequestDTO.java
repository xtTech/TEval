package com.tairanchina.teval.service.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 27 9:54
 */
@ApiModel(description = "token验证参数")
public class ValidateTokenRequestDTO {
    @ApiModelProperty(name = "用户token",notes = "用户token")
    @NotBlank(message = "token不能为空")
    private String token;

    public String getToken() {
        return token;
    }

    public ValidateTokenRequestDTO setToken(String token) {
        this.token = token;
        return this;
    }
}
