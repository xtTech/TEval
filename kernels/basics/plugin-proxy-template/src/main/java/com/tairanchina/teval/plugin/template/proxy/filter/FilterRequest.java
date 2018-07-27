package com.tairanchina.teval.plugin.template.proxy.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterRequest {

    private Function<String, String[]> cookieLazyFetchProcessor;

    private String remoteIP;
    private String protocol;
    private String host;
    private int port;
    private String path;
    private Map<String, String> query;
    private Map<String, String> headers;

    public FilterRequest(Function<String, String[]> cookieLazyFetchProcessor) {
        this.cookieLazyFetchProcessor = cookieLazyFetchProcessor;
    }

    public String queryToString() {
        try {
           return URLEncoder.encode(query.entrySet().stream()
                            .map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&")),
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getCookie(String cookieName) {
        return cookieLazyFetchProcessor.apply(cookieName);
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public FilterRequest setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public FilterRequest setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getHost() {
        return host;
    }

    public FilterRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public FilterRequest setPort(int port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public FilterRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public FilterRequest setQuery(Map<String, String> query) {
        this.query = query;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public FilterRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public String toString() {
        return protocol + "://" + host + ":" + port + path;
    }
}
