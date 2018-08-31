package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.dto.MailRequestDTO;
import com.tairanchina.teval.service.admin.dto.RegisterMailRequestDTO;
import com.tairanchina.teval.service.admin.dto.ResetPwdMailRequestDTO;
import com.tairanchina.teval.service.admin.dto.SendMailResponseDTO;

/**
 * 描述:
 * 邮件发送
 *
 * @author hzds
 * @Create 2018-08 : 08 13:37
 */
public interface EmailService {
    /**
     * 邮件发送服务
     * @param requestDTO
     * @return
     */
    Resp<Boolean> sendEmail(MailRequestDTO requestDTO);

    /**
     * 注册邮件发送
     * @param requestDTO
     * @return
     */
    Resp<SendMailResponseDTO> sendRegisterMail(RegisterMailRequestDTO requestDTO);

    /**
     * 重置密码邮件发送
     * @param requestDTO
     * @return
     */
    Resp<SendMailResponseDTO> sendResetPwdMail(ResetPwdMailRequestDTO requestDTO);
}
