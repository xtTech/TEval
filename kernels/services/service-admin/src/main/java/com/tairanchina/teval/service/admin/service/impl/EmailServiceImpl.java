package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.csp.dew.Dew;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.common.dao.AccountDao;
import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.service.admin.dto.MailRequestDTO;
import com.tairanchina.teval.service.admin.dto.RegisterMailRequestDTO;
import com.tairanchina.teval.service.admin.dto.ResetPwdMailRequestDTO;
import com.tairanchina.teval.service.admin.dto.SendMailResponseDTO;
import com.tairanchina.teval.service.admin.service.AccountIdentityService;
import com.tairanchina.teval.service.admin.service.EmailService;
import com.tairanchina.teval.service.admin.util.EmailSendUtil;
import com.tairanchina.teval.service.admin.util.EncryptUtils;
import com.tairanchina.teval.service.admin.vo.AccountIdentityVo;
import com.tairanchina.teval.service.admin.vo.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 * 邮件发送服务实现
 *
 * @author hzds
 * @Create 2018-08 : 08 14:37
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private EmailMessage message;//TODO:发送邮件相关配置暂时默认

    @Autowired
    private AccountIdentityService identityService;

    @Autowired
    private AccountDao accountDao;

    @Override
    public Resp<Boolean> sendEmail(MailRequestDTO requestDTO) {
        return EmailSendUtil.sendEmail(requestDTO, message);
    }

    @Override
    @Transactional
    public Resp<SendMailResponseDTO> sendRegisterMail(RegisterMailRequestDTO requestDTO) {
        //生成密码加密盐
        String encryptSalt = $.field.createUUID();
        //密码加密
        String encryptPwd = EncryptUtils.encrypt(requestDTO.getPassword(), encryptSalt);
        //创建非激活账户
        String accountId = $.field.createUUID();
        Account account = new Account();
        account.setId(accountId);
        account.setStatus(EntityObject.Status.INACTIVE);
        account.setCreatedBy(Global.SYSTEM_CORE_ID).setUpdatedBy(Global.SYSTEM_CORE_ID);
        accountDao.insert(account);
        //创建非激活账户凭证
        AccountIdentityVo vo = new AccountIdentityVo()
                .setEmail(requestDTO.getEmail())
                .setPasswordEncryptSalt(encryptSalt)
                .setPassword(encryptPwd)
                .setStatus(EntityObject.Status.INACTIVE.toString())
                .setCreatedBy(accountId)
                .setUpdatedBy(accountId);
        Resp<Boolean> saveResp = identityService.saveAccountIdentity(vo);
        if (!saveResp.ok()) {
            return Resp.error(saveResp);
        }
        //创建验证身份识别码缓存
        String code = $.field.createShortUUID();
        //设置激活邮件有效时间
        Long actvieMinutes = 10L;

        Dew.cluster.cache.setex(Global.V_REGISTER_CODE_ID + code, requestDTO.getEmail(), actvieMinutes * 60);
        //构建邮件体
        String contentHtml = "<p style='text-indent:2em;'>\n" +
                "        HI，服务市场用户 你好!<br/><br/>\n" +
                "        你正在进行注册操作，请点击一下链接，完成注册：\n" +
                "        <a href=\"" + Global.ACTIVE_ACCOUNT_URL + "?code=" + code + "&email=" + requestDTO.getEmail() + "\">" + Global.ACTIVE_ACCOUNT_URL + "?code=" + code + "&email=" + requestDTO.getEmail() + "</a>。\n" +
                "        如果这不是你的邮件请忽略，很抱歉打扰你，请原谅。\n" +
                "    </p>";
        MailRequestDTO mailRequest = new MailRequestDTO()
                .setSubject("服务市场注册")
                .setTo(requestDTO.getEmail())
                .setHeader("TEval Service")
                .setContentType(EmailMessage.ContentType.HTML.toString())
                .setContent(contentHtml);
        Resp<Boolean> sendResp = EmailSendUtil.sendEmail(mailRequest, message);
        if (!sendResp.ok()) {
            return Resp.error(sendResp);
        }
        return Resp.success(new SendMailResponseDTO()
                .setSendEmail(requestDTO.getEmail())
                .setMessage("注册邮件已发送至【" + requestDTO.getEmail() + "】邮箱,请在" + actvieMinutes + "分钟内完成注册操作")
                .setEffectiveMinutes(actvieMinutes));
    }

    @Override
    public Resp<SendMailResponseDTO> sendResetPwdMail(ResetPwdMailRequestDTO requestDTO) {
        //创建验证身份识别码缓存
        String code = $.field.createShortUUID();
        //设置激活邮件有效时间
        Long actvieMinutes = 10L;

        Dew.cluster.cache.setex(Global.V_RESETPWD_CODE_ID + code, requestDTO.getEmail(), actvieMinutes * 60);
        //构建邮件体
        String contentHtml = "<p style='text-indent:2em;'>\n" +
                "        HI，服务市场用户 你好!<br/><br/>\n" +
                "        你正在进行登录密码找回，请点击一下链接，完成密码重置：\n" +
                "        <a href=\"" + Global.RESET_PASSWORD_URL + "?code=" + code + "&email=" + requestDTO.getEmail() + "\">" + Global.RESET_PASSWORD_URL + "?code=" + code + "&email=" + requestDTO.getEmail() + "</a>。\n" +
                "        如果这不是你的邮件请忽略，很抱歉打扰你，请原谅。\n" +
                "    </p>";
        MailRequestDTO mailRequest = new MailRequestDTO()
                .setSubject("服务市场密码找回")
                .setTo(requestDTO.getEmail())
                .setHeader("TEval Service")
                .setContentType(EmailMessage.ContentType.HTML.toString())
                .setContent(contentHtml);
        Resp<Boolean> sendResp = EmailSendUtil.sendEmail(mailRequest, message);
        if (!sendResp.ok()) {
            return Resp.error(sendResp);
        }
        return Resp.success(new SendMailResponseDTO()
                .setSendEmail(requestDTO.getEmail())
                .setMessage("密码重置邮件已发送至【" + requestDTO.getEmail() + "】邮箱,请在" + actvieMinutes + "分钟内完成重置密码操作")
                .setEffectiveMinutes(actvieMinutes));
    }
}
