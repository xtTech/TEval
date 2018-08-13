package com.tairanchina.teval.service.admin.util;

import org.springframework.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:邮件发送工具类
 *
 * @author hzds
 * @Create 2018-08 : 07 19:41
 */
public class EmailSendUtil {

    public static Address[] convertStrToAddress(String str) throws AddressException {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str = str.replace(" ", ""))) return null;
        String[] split = str.split(",|;");
        List<String> splitArrays = new ArrayList<>(split.length);
        for (int i = 0; i < split.length; i++) {
            if (!StringUtils.isEmpty(split[i]) && !splitArrays.contains(split[i])) {
                splitArrays.add(split[i]);
            }
        }
        Address[] addresses = split.length == 0 ? null : new Address[splitArrays.size()];
        for (int i = 0; i < splitArrays.size(); i++) {
            addresses[i] = new InternetAddress(splitArrays.get(i));
        }
        return addresses;
    }
}
