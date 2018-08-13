package com.tairanchina.teval.service.crypto.controller;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.service.crypto.com.config.CryptoConfig;
import com.tairanchina.teval.service.crypto.com.interceptor.Identify;
import com.tairanchina.teval.service.crypto.model.dto.Cipher;
import com.tairanchina.teval.service.crypto.model.dto.KeyValue;
import com.tairanchina.teval.service.crypto.model.enumeration.EncryptionAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/10.
 */
@RestController
@RequestMapping(path = "/crypto/symmetric",
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class SymmetricCryptoController {

    @Autowired
    private CryptoConfig cryptoConfig;

    private List<KeyValue> encrypt(List<KeyValue> keyValueList, EncryptionAlgorithm algorithm) {
        return keyValueList.stream().map(keyValue -> {
            try {
                String randomId = $.field.createUUID();
                String password = $.security.digest.digest(cryptoConfig.getSeed() + randomId, EncryptionAlgorithm.MD5.getValue());
                return new KeyValue(keyValue.getKey(), new Cipher($.security.symmetric.encrypt(keyValue.getValue(), password, algorithm.getValue()), randomId).toString());
            } catch (GeneralSecurityException e) {
                return keyValue;
            }
        }).collect(Collectors.toList());
    }

    private List<KeyValue> decrypt(List<KeyValue> keyValueList, EncryptionAlgorithm algorithm) {
        return keyValueList.stream().map(keyValue -> {
            Cipher cipher = Cipher.parse(keyValue.getValue());
            if(cipher == null) return keyValue;
            try {
                String randomId = cipher.getRandomId();
                String password = $.security.digest.digest(cryptoConfig.getSeed() + randomId, EncryptionAlgorithm.MD5.getValue());
                return new KeyValue(keyValue.getKey(), $.security.symmetric.decrypt(cipher.getCipherText(), password, algorithm.getValue()));
            } catch (GeneralSecurityException e) {
                return keyValue;
            }
        }).collect(Collectors.toList());
    }

    // @Identify
    @RequestMapping(method = RequestMethod.POST, path = "/des/encrypt")
    public Resp<List<KeyValue>> encryptByDES(
            @Valid @RequestBody List<KeyValue> keyValueList
            ) {
        return Resp.success(encrypt(keyValueList, EncryptionAlgorithm.SYMMETRIC_DES));
    }

    // @Identify
    @RequestMapping(method = RequestMethod.POST, path = "/des/decrypt")
    public Resp<List<KeyValue>> decryptByDES(
            @Valid @RequestBody List<KeyValue> keyValueList
    ) {
        return Resp.success(decrypt(keyValueList, EncryptionAlgorithm.SYMMETRIC_DES));
    }

    // @Identify
    @RequestMapping(method = RequestMethod.POST, path = "/aes/encrypt")
    public Resp<List<KeyValue>> encryptByAES(
            @Valid @RequestBody List<KeyValue> keyValueList
    ) {
        return Resp.success(encrypt(keyValueList, EncryptionAlgorithm.SYMMETRIC_AES));
    }

    // @Identify
    @RequestMapping(method = RequestMethod.POST, path = "/aes/decrypt")
    public Resp<List<KeyValue>> decryptByAES(
            @Valid @RequestBody List<KeyValue> keyValueList
    ) {
        return Resp.success(decrypt(keyValueList, EncryptionAlgorithm.SYMMETRIC_AES));
    }
}
