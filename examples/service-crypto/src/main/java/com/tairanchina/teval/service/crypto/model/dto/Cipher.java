package com.tairanchina.teval.service.crypto.model.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class Cipher {

    private String cipherText;
    private String randomId;

    public Cipher(String cipherText, String randomId) {
        this.cipherText = cipherText;
        this.randomId = randomId;
    }

    public String getCipherText() {
        return cipherText;
    }

    public Cipher setCipherText(String cipherText) {
        this.cipherText = cipherText;
        return this;
    }

    public String getRandomId() {
        return randomId;
    }

    public Cipher setRandomId(String randomId) {
        this.randomId = randomId;
        return this;
    }

    @Override
    public String toString() {
        return "CipherText{" + cipherText + "," + randomId + "}";
    }

    public static Cipher parse(String cipherText) {
        Pattern pattern = Pattern.compile("^CipherText\\{(?<cipherText>.*?),(?<randomId>.*?)\\}$");
        Matcher matcher = pattern.matcher(cipherText);
        if(matcher.matches()) {
            return new Cipher(matcher.group("cipherText"), matcher.group("randomId"));
        }
        return null;
    }
}
