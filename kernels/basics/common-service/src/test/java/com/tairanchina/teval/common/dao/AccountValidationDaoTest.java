package com.tairanchina.teval.common.dao;

import com.tairanchina.teval.common.BaseTest;
import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.account.AccountValidation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 21 10:13
 */
public class AccountValidationDaoTest extends BaseTest {

    @Autowired
    private AccountValidationDao  accountValidationDao;

    @Test
    public void getByPrimaryKey(){
        AccountValidation validation = accountValidationDao.getByPrimaryKey(1);
        System.out.println(validation);
    }
    @Test
    public void findByCondition(){
        List<AccountValidation> accountValidations = accountValidationDao.findByCondition(new AccountValidation().setValidated(false));
        Assert.assertTrue(accountValidations.size() > 0);
    }
    @Test
    public void insert(){
        AccountValidation validation = new AccountValidation();
        validation.setCode("2035");
        validation.setErrorTimes(0);
        validation.setValidated(false);
        validation.setCreatedBy("2953508b98b54b2c8a9db45a2df73d4a");
        validation.setUpdatedBy("2953508b98b54b2c8a9db45a2df73d4a");
        validation.setFromDate(new Date());
        validation.setThruDate(new Date(System.currentTimeMillis()+10*24*60*60*1000));
        validation.setStatus(EntityObject.Status.INACTIVE);
        validation.setRemark("测试数据");
        int insert = accountValidationDao.insert(validation);
        Assert.assertTrue(insert > 0);
    }
    @Test
    public void delete(){
        int i = accountValidationDao.deleteByPrimaryKey(1);
        Assert.assertTrue(i > 0);
    }
}
