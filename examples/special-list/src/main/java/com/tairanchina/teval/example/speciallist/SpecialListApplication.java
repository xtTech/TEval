package com.tairanchina.teval.example.speciallist;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpecialListApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpecialListApplication.class).web(true).run(args);
    }

}
