package com.tairanchina.teval.example.speciallist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SpecialListController {

    @GetMapping(value = "/bl/{userId}")
    public boolean hitBlackList(@PathVariable("userId") String userId) {
        return false;
    }

}
