package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.dto.AccountTokenRequestDTO;
import com.tairanchina.teval.service.admin.dto.ValidateTokenResponseDTO;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 17:46
 */
public interface AccountTokenService {
    /**
     * 登录返回Token创建
     * @param requestDTO
     * @return
     */
    Resp<String> createAccountToken(AccountTokenRequestDTO requestDTO);

    /**
     * 用户请求接口Token校验
     * @param token
     * @return
     */
    Resp<ValidateTokenResponseDTO> validateToken(String token);

}
