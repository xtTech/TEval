package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.dto.ResetPwdRequestDTO;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 27 17:34
 */
public interface PasswordService {
    /**
     * 重置密码接口
     * @param requestDTO
     * @return
     */
    Resp<Boolean> resetPassword(ResetPwdRequestDTO requestDTO);
}
