package com.tairanchina.teval.service.console;

import com.tairanchina.teval.TEvalApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ConsoleApplication extends TEvalApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsoleApplication.class).web(true).run(args);
    }

}
