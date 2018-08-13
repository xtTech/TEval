package com.tairanchina.teval.example.loan;

import com.ecfront.dew.common.$;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @GetMapping(value = "/{userId}/credit")
    public long getCreditAmount(@PathVariable("userId") String userId) throws IOException {
        if (Boolean.valueOf($.http.get("http://sl.teval.virtual/bl/" + userId))) {
            return 0L;
        }
        return 10000L;
    }

}
