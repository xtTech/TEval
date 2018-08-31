package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.Resp;
import com.tairanchina.csp.dew.Dew;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.common.dao.AccountDao;
import com.tairanchina.teval.common.dao.AccountIdentDao;
import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountIdentityExt;
import com.tairanchina.teval.service.admin.service.AccountIdentityService;
import com.tairanchina.teval.service.admin.vo.AccountIdentityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 23 14:33
 */
@Service
public class AccountIdentityServiceImpl implements AccountIdentityService {

    @Autowired
    private AccountIdentDao identDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public Resp<Boolean> saveAccountIdentity(AccountIdentityVo vo) {
        List<AccountIdentity> exitsIdentity = identDao.findByCondition(new AccountIdentity().setEmail(vo.getEmail()));
        if (exitsIdentity.size() > 0) {
            return Global.RespError.BAD_ARGUMENT.detail("账户邮箱[%v]已存在", vo.getEmail()).get();
        }
        AccountIdentity identity = new AccountIdentity();
        identity.setEmail(vo.getEmail())
                .setPassword(vo.getPassword())
                .setPasswordEncryptSalt(vo.getPasswordEncryptSalt())
                .setStatus(EntityObject.Status.valueOf(vo.getStatus()))
                .setRemark(vo.getRemark());
        identity.setFromDate(vo.getFromDate()).setThruDate(vo.getThruDate());
        identity.setCreatedBy(vo.getCreatedBy() == null ? Global.SYSTEM_CORE_ID : vo.getCreatedBy());
        identity.setUpdatedBy(vo.getUpdatedBy() == null ? Global.SYSTEM_CORE_ID : vo.getUpdatedBy());
        int insert = identDao.insert(identity);
        return Resp.success(insert > 0);
    }

    @Override
    public Resp<Boolean> activeAccountByEmail(String email, String code) {
        String paramEmail = Dew.cluster.cache.get(Global.V_REGISTER_CODE_ID + code);
        if (StringUtils.isEmpty(paramEmail)) {
            return Global.RespError.EMPTY_VALUE.detail("激活邮件已失效").get();
        }
        if (!paramEmail.equals(email)) {
            return Global.RespError.DATA_BROKEN.detail("系统出错").get();
        }
        AccountIdentityExt identitiyExt = identDao.getExtByIdentity(new AccountIdentity().setEmail(email));
        if (identitiyExt == null) {
            return Global.RespError.EMPTY_VALUE.detail("账户邮箱不存在").get();
        }
        //激活账户信息
        Account account = identitiyExt.getAccount();
        account.setStatus(EntityObject.Status.ACTIVE);
        accountDao.updateByPrimaryKey(account.getId(),account);
        //激活账户凭证
        identitiyExt.setStatus(EntityObject.Status.ACTIVE);
        return Resp.success(identDao.updateByPrimaryKey(identitiyExt.getId(), identitiyExt) > 0);
    }

}
