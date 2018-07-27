package com.tairanchina.teval.service.proxy.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHelper {

    public static void parseQuery(String queryStr, Map<String, String> query) {
        String[] items = new String[0];
        try {
            items = URLDecoder.decode(queryStr, "UTF-8").split("&");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String item : items) {
            String[] entry = item.split("=");
            String key = entry[0];
            String value = entry.length == 2 ? entry[1] : null;
            if (value != null) {
                query.put(key, value);
            }
        }
    }

    public static Map<String, String> parseQuery(String queryStr) {
        Map<String, String> query = new HashMap<>();
        parseQuery(queryStr, query);
        return query;
    }

}
