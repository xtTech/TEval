package com.tairanchina.teval.service.gateway;

import com.tairanchina.csp.dew.test.DewTestAutoConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GatewayApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = DewTestAutoConfiguration.class)
public class BaseTest {

    @Test
    public void testSome() {
        // Demo
        Assert.assertTrue(1 == 1);
    }

}
