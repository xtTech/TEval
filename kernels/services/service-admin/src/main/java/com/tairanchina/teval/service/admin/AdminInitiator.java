package com.tairanchina.teval.service.admin;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminInitiator {

    @PostConstruct
    public void init() {
        System.out.println("初始化函数！");
    }
}
