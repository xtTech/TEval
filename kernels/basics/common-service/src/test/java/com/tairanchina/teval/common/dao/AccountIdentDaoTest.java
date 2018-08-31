package com.tairanchina.teval.common.dao;

import com.tairanchina.teval.common.BaseTest;
import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;
import com.tairanchina.teval.common.domain.core.ext.condition.ExtAccountIdentCondition;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountIdentityExt;
import com.tairanchina.teval.common.domain.core.ext.options.ExtAccountIdentOptions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 17 17:22
 */
public class AccountIdentDaoTest extends BaseTest {
    @Autowired
    private AccountIdentDao accountIdentDao;

    @Test
    public void getByPrimaryKey() {
        AccountIdentity byPrimaryKey = accountIdentDao.getByPrimaryKey(1L);
        System.out.println(byPrimaryKey);
    }

    @Test
    public void insert() {
        AccountIdentity identity = new AccountIdentity();
        identity.setEmail("1234@qq.com");
        identity.setStatus(EntityObject.Status.ACTIVE);
        identity.setPassword("123456..");
        identity.setPasswordEncryptSalt("passwordEncryptSalt");
//        identity.setThruDate(new Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000));
//        identity.setFromDate(new Date());
        identity.setCreatedBy("2953508b98b54b2c8a9db45a2df73d4a");
        identity.setUpdatedBy("2953508b98b54b2c8a9db45a2df73d4a");
        identity.setRemark("测试");
        int insert = accountIdentDao.insert(identity);
        Assert.assertTrue(insert > 0);
    }

    @Test
    public void findByCondition() {
        AccountIdentity identity = new AccountIdentity();
        identity.setStatus(EntityObject.Status.ACTIVE);
        List<AccountIdentity> byCondition = accountIdentDao.findByCondition(identity);
        Assert.assertTrue(!byCondition.isEmpty());
    }

    @Test
    public void delete() {
        int r = accountIdentDao.deleteByPrimaryKey(1);
        Assert.assertTrue(r > 0);
    }

    @Test
    public void findExtByCondition() {
        List<AccountIdentityExt> identityExts = accountIdentDao.findExtByCondition(new AccountIdentity(), new ExtAccountIdentCondition().setIdentFuzzyMatch("hduds1314kx"), null, new ExtAccountIdentOptions().setExtAccount(true));
        Assert.assertTrue(identityExts.size() > 0);
    }

    @Test
    public void findExtByPrimaryKey(){
        AccountIdentityExt extByPrimaryKey = accountIdentDao.getExtByPrimaryKey(1L);
        Assert.assertTrue(extByPrimaryKey != null);
    }
    @Test
    public void findExtByIdentity(){
        AccountIdentity identity = new AccountIdentity().setEmail("hduds1314kx@163.com");
        identity.setStatus(EntityObject.Status.ACTIVE);
        AccountIdentityExt extByPrimaryKey = accountIdentDao.getExtByIdentity(identity);
        Assert.assertTrue(extByPrimaryKey != null);
    }
}
