package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.csp.dew.Dew;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.common.dao.AccountIdentDao;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;
import com.tairanchina.teval.service.admin.dto.ResetPwdRequestDTO;
import com.tairanchina.teval.service.admin.service.PasswordService;
import com.tairanchina.teval.service.admin.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 27 17:37
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private AccountIdentDao accountIdentDao;

    @Override
    public Resp<Boolean> resetPassword(ResetPwdRequestDTO requestDTO) {
        String paramEmail = Dew.cluster.cache.get(Global.V_RESETPWD_CODE_ID + requestDTO.getCode());
        if (StringUtils.isEmpty(paramEmail)) {
            return Global.RespError.EMPTY_VALUE.detail("密码重置邮件已失效").get();
        }
        if (!paramEmail.equals(requestDTO.getEmail())) {
            return Global.RespError.DATA_BROKEN.detail("系统出错").get();
        }
        List<AccountIdentity> identities = accountIdentDao.findByCondition(new AccountIdentity().setEmail(requestDTO.getEmail()));
        if (identities.isEmpty()) {
            return Global.RespError.EMPTY_VALUE.detail("当前账户[%v]不存在", requestDTO.getEmail()).get();
        }
        AccountIdentity identity = identities.get(0);
        //更新加密盐
        String newEncryptSalt = $.field.createUUID();
        String newPassword = EncryptUtils.encrypt(requestDTO.getNewPassword(), newEncryptSalt);
        identity.setPassword(newPassword);
        identity.setPasswordEncryptSalt(newEncryptSalt);
        return Resp.success(accountIdentDao.updateByPrimaryKey(identity.getId(), identity) > 0);
    }
}
