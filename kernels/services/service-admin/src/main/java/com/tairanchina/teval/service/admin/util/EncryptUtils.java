package com.tairanchina.teval.service.admin.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述: 加密工具(只支持SHA-256加密)
 *
 * @author hzds
 * @Create 2018-08 : 23 11:25
 */
public class EncryptUtils {

    private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    public static String encrypt(String content, String encryptSalt){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(content.getBytes());
            String encrypted = String.valueOf(Hex.encode(messageDigest.digest()));
            if(StringUtils.isEmpty(encryptSalt)) {
                return encrypted;
            } else {
                encrypted = encrypted + encryptSalt;
                messageDigest.update(encrypted.getBytes());
                encrypted = String.valueOf(Hex.encode(messageDigest.digest()));
                return encrypted;
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            logger.error("密码加密出错！");
            return null;
        }
    }
}
