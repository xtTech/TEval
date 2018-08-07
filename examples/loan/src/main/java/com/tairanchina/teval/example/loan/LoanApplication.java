package com.tairanchina.teval.example.loan;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;

@SpringBootApplication
public class LoanApplication {

    public static void main(String[] args) throws IOException {
        new SpringApplicationBuilder(LoanApplication.class).web(true).run(args);
    }

}
