package com.tairanchina.teval.common.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 16 10:37
 */
@Configuration
@MapperScan(basePackages = {"com.tairanchina.teval.common.dao"})
public class MybatisScanConfiguration {
}
