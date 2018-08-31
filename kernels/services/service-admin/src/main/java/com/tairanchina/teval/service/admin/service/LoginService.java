package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.dto.LoginRequestDTO;
import com.tairanchina.teval.service.admin.dto.LoginResponseDTO;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 16:20
 */
public interface LoginService {
    /**
     * 用户邮箱登录
     * @param requestDTO
     * @return
     */
    Resp<LoginResponseDTO> emailAndPasswordLogin(LoginRequestDTO requestDTO);
}
