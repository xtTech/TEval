package com.tairanchina.teval.common.dao;

import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.ext.condition.ExtAccountCondition;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountExt;
import com.tairanchina.teval.common.domain.core.ext.options.ExtAccountOptions;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 16 10:37
 */
@Repository
public interface AccountDao extends BaseDao<Account>{

    default AccountExt getExtByPrimaryKey(String primaryKey){
        Account account = new Account();
        account.setId(primaryKey);
        List<AccountExt> extList = findExtByCondition(account, new ExtAccountCondition(), null, new ExtAccountOptions().setExtAccountIdentity(true));
        if(extList.isEmpty()){
            return null;
        }
        return extList.get(0);
    }

    List<AccountExt> findExtByCondition(@Param("_") Account baseCondition, @Param("c") ExtAccountCondition condition, @Param("extraSQL")String extraSQL, @Param("ext") ExtAccountOptions extOptions);

}
