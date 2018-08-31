package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.common.dao.AccountIdentDao;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountIdentityExt;
import com.tairanchina.teval.service.admin.dto.AccountTokenRequestDTO;
import com.tairanchina.teval.service.admin.dto.LoginRequestDTO;
import com.tairanchina.teval.service.admin.dto.LoginResponseDTO;
import com.tairanchina.teval.service.admin.service.AccountTokenService;
import com.tairanchina.teval.service.admin.service.LoginService;
import com.tairanchina.teval.service.admin.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 16:22
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountIdentDao accountIdentDao;

    @Autowired
    private AccountTokenService tokenService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Resp<LoginResponseDTO> emailAndPasswordLogin(LoginRequestDTO requestDTO) {
        AccountIdentityExt identityExt = accountIdentDao.getExtByIdentity(new AccountIdentity().setEmail(requestDTO.getEmail()));
        if (identityExt == null) {
            return Global.RespError.EMPTY_VALUE.detail("账户[%v]不存在", requestDTO.getEmail()).get();
        }
        String loginPassword = EncryptUtils.encrypt(requestDTO.getPassword(), identityExt.getPasswordEncryptSalt());
        if (!loginPassword.equals(identityExt.getPassword())) {
            return Global.RespError.BAD_ARGUMENT.detail("账户密码有误").get();
        }
        //更新加密盐
        String newEncryptSalt = $.field.createUUID();
        //重新对密码加密
        String password = EncryptUtils.encrypt(requestDTO.getPassword(), newEncryptSalt);
        //更新
        accountIdentDao.updateByPrimaryKey(identityExt.getId(), identityExt.setPassword(password).setPasswordEncryptSalt(newEncryptSalt));
        //构造返回结果
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        Account account = identityExt.getAccount();
        if (account != null) {
            responseDTO.setAccount_secret(account.getAccountSecret())
                    .setAvatar(account.getAvatar())
                    .setDescription(account.getDescription())
                    .setName(account.getName())
                    .setRemark(account.getRemark())
                    .setStatus(account.getStatus().toString())
                    .setAccountId(account.getId())
                    .setIdentityId(identityExt.getId());
        }
        AccountTokenRequestDTO accountTokenRequestDTO = new AccountTokenRequestDTO()
                .setEmail(requestDTO.getEmail())
                .setAccountId(account.getId())
                .setIdentId(identityExt.getId())
                .setEncPassword(password)
                .setExpMinutes(requestDTO.getRememberMeMinutes());

        //获取token
        Resp<String> accountToken = tokenService.createAccountToken(accountTokenRequestDTO);
        responseDTO.setToken(accountToken.getBody());
        return Resp.success(responseDTO);
    }
}
