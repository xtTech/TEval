package com.tairanchina.teval.service.crypto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/10.
 */
@SpringBootApplication
public class CryptoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CryptoApplication.class).web(true).run(args);
    }
}
