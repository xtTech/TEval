package com.tairanchina.teval.common.dao;

import com.ecfront.dew.common.$;
import com.tairanchina.teval.common.BaseTest;
import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 22 15:25
 */
public class ServiceDaoTest extends BaseTest {

    @Autowired
    private ServiceDao serviceDao;

    @Test
    public void insert(){
        Service service = new Service();
        service.setId($.field.createUUID());
        service.setCreatedBy("2953508b98b54b2c8a9db45a2df73d4a");
        service.setUpdatedBy("2953508b98b54b2c8a9db45a2df73d4a");
        service.setStatus(EntityObject.Status.INACTIVE);
        service.setVersion(1);
        service.setDescription("这是测试用的案例");
        service.setConfigGroupIds(new ArrayList<String>(){{
            add("u87sd");
            add("9uyer");
            add("sds8k");
        }});
        int i = serviceDao.insert(service);
        Assert.assertTrue(i>0);
    }
    @Test
    public void getServiceById(){
        Service service = serviceDao.getByPrimaryKey("8700508769aa4324862a646448d06833");
        Assert.assertTrue(service != null);
    }
    @Test
    public void findByCondition(){
        List<Service> service = serviceDao.findByCondition((Service) new Service().setStatus(EntityObject.Status.ACTIVE));
        Assert.assertTrue(service.size()>0);
    }
    @Test
    public void updateByPrimaryKey(){

        Service service = serviceDao.getByPrimaryKey("8700508769aa4324862a646448d06833");
        service.setVersion(2);
        service.setStatus(EntityObject.Status.ACTIVE);
        service.setDescription("是滴撒达克赛德卡萨丁");
        int i = serviceDao.updateByPrimaryKey(service.getId(), service);
        Assert.assertTrue(i>0);
    }
    @Test
    public void deleteByPrimaryKey(){

    }
}
