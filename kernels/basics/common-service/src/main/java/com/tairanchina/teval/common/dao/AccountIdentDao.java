package com.tairanchina.teval.common.dao;

import com.tairanchina.teval.common.domain.core.account.AccountIdentity;
import com.tairanchina.teval.common.domain.core.ext.condition.ExtAccountIdentCondition;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountIdentityExt;
import com.tairanchina.teval.common.domain.core.ext.options.ExtAccountIdentOptions;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 17 16:51
 */
@Repository
public interface AccountIdentDao extends BaseDao<AccountIdentity>{

    default AccountIdentityExt getExtByPrimaryKey(Long primaryKey){
        AccountIdentity identity = new AccountIdentity();
        identity.setId(primaryKey);
        List<AccountIdentityExt> accountIdentityExts = findExtByCondition(identity, new ExtAccountIdentCondition(), null, new ExtAccountIdentOptions().setExtAccount(true));
        if(accountIdentityExts.isEmpty()){
            return null;
        }
        return accountIdentityExts.get(0);
    }
    default AccountIdentityExt getExtByIdentity(AccountIdentity identity){
        List<AccountIdentityExt> accountIdentityExts = findExtByCondition(identity, new ExtAccountIdentCondition(), null, new ExtAccountIdentOptions().setExtAccount(true));
        if(accountIdentityExts.isEmpty()){
            return null;
        }
        return accountIdentityExts.get(0);
    }
    List<AccountIdentityExt> findExtByCondition(@Param("_") AccountIdentity baseCondition, @Param("c") ExtAccountIdentCondition condition, @Param("extraSQL")String extraSQL, @Param("ext") ExtAccountIdentOptions extOptions);
}
