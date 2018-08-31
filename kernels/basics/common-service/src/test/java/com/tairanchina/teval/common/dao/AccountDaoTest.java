package com.tairanchina.teval.common.dao;

import com.ecfront.dew.common.$;
import com.tairanchina.teval.common.BaseTest;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.ext.condition.ExtAccountCondition;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountExt;
import com.tairanchina.teval.common.domain.core.ext.options.ExtAccountOptions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 16 11:31
 */
//@Transactional
public class AccountDaoTest extends BaseTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void findByPrimaryKey(){
        Account account = accountDao.getByPrimaryKey("dssdwds");
        System.out.println("2342342");
    }
    @Test
    public void findByCondition(){
        Account accout = new Account();
        accout.setStatus(Account.Status.ACTIVE);
        List<Account> account = accountDao.findByCondition(accout);
        System.out.println("2342342");
    }
    @Test
    public void saveAccount(){
        Account accout = new Account();
        accout.setId($.field.createUUID()).setCreatedBy("dssdwds").setUpdatedBy("dssdwds");
        accout.setAccountSecret("5002211XXXX69XXX").setAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534421137403&di=d15def0eaa6c8899a1e0eeaf2f257af6&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D3198678185%2C878755003%26fm%3D214%26gp%3D0.jpg");
        accout.setName("小明");
        accout.setStatus(Account.Status.ACTIVE);
        accout.setCreatedTime(new Date());
        accout.setUpdatedTime(new Date());
        int i = accountDao.insert(accout);
        Assert.assertTrue(i>0);
    }

    @Test
    public void updateByPrimaryKey(){
        String keyId = "2953508b98b54b2c8a9db45a2df73d4a";
        Account account = accountDao.getByPrimaryKey(keyId);
        int i = accountDao.updateByPrimaryKey(keyId, (Account)account.setDescription("测试更新23131").setName("阿里啊lala").setStatus(Account.Status.ACTIVE).setRemark("111222"));
        Assert.assertTrue(i>0);
    }
    @Test
    public void deleteByPrimaryKey(){
        String keyId = "2953508b98b54b2c8a9db45a2df73d4a";
        int i = accountDao.deleteByPrimaryKey(keyId);
        Assert.assertTrue(i>0);
    }
    @Test
    public void findExtByCondition(){
        List<AccountExt> ext = accountDao.findExtByCondition(new Account(), new ExtAccountCondition().setAccountFuzzyMatch("薛文"), null, new ExtAccountOptions().setExtAccountIdentity(true));
        Assert.assertTrue(ext.size() > 0 );
    }

}
