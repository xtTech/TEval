package com.tairanchina.teval.service.crypto.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/10.
 */
@RestController
@RequestMapping(path = "/crypto/asymmetric",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class AsymmetricCryptoController {

}
