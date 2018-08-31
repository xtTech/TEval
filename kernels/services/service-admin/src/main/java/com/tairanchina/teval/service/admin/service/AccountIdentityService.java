package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.admin.vo.AccountIdentityVo;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 23 9:34
 */
public interface AccountIdentityService {
    /**
     * 保存账户登录凭证信息
     * @param vo
     * @return
     */
    Resp<Boolean> saveAccountIdentity(AccountIdentityVo vo);

    /**
     * 邮箱激活账户
     * @param email
     * @param code
     * @return
     */
    Resp<Boolean> activeAccountByEmail(String email,String code);
}
