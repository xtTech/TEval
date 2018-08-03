package com.tairanchina.teval.common.dto;

import java.util.*;

public class APIConfig {

    private static final int DEFAULT_REDIRECT_TIMEOUT_MS = 60000;

    private String proxyAppId;
    private String proxyMethod;
    private String proxyPath;
    private Map<String, Set<String>> proxyQueryOpt = new HashMap<>();
    private Map<String, Set<String>> proxyHeaderOpt = new HashMap<>();
    private String redirectProtocol;
    private String redirectHost;
    private int redirectPort;
    private String redirectPath;
    private Map<String, String> redirectQueryOpt = new HashMap<>();
    private Map<String, String> redirectAddHeadersOpt = new HashMap<>();
    private Set<String> redirectRemoveHeadersOpt = new HashSet<>();
    private Map<String, String> redirectAddCookiesOpt = new HashMap<>();
    private Set<String> redirectRemoveCookiesOpt = new HashSet<>();
    private int redirectTimeoutMs = DEFAULT_REDIRECT_TIMEOUT_MS;
    private List<String> filterIds;
    private String mockSrc;

    public String getProxyAppId() {
        return proxyAppId;
    }

    public APIConfig setProxyAppId(String proxyAppId) {
        this.proxyAppId = proxyAppId;
        return this;
    }

    public String getProxyMethod() {
        return proxyMethod;
    }

    public APIConfig setProxyMethod(String proxyMethod) {
        this.proxyMethod = proxyMethod;
        return this;
    }

    public String getProxyPath() {
        return proxyPath;
    }

    public APIConfig setProxyPath(String proxyPath) {
        this.proxyPath = proxyPath;
        return this;
    }

    public Map<String, Set<String>> getProxyQueryOpt() {
        return proxyQueryOpt;
    }

    public APIConfig setProxyQueryOpt(Map<String, Set<String>> proxyQueryOpt) {
        this.proxyQueryOpt = proxyQueryOpt;
        return this;
    }

    public Map<String, Set<String>> getProxyHeaderOpt() {
        return proxyHeaderOpt;
    }

    public APIConfig setProxyHeaderOpt(Map<String, Set<String>> proxyHeaderOpt) {
        this.proxyHeaderOpt = proxyHeaderOpt;
        return this;
    }

    public String getRedirectProtocol() {
        return redirectProtocol;
    }

    public APIConfig setRedirectProtocol(String redirectProtocol) {
        this.redirectProtocol = redirectProtocol;
        return this;
    }

    public String getRedirectHost() {
        return redirectHost;
    }

    public APIConfig setRedirectHost(String redirectHost) {
        this.redirectHost = redirectHost;
        return this;
    }

    public int getRedirectPort() {
        return redirectPort;
    }

    public APIConfig setRedirectPort(int redirectPort) {
        this.redirectPort = redirectPort;
        return this;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public APIConfig setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
        return this;
    }

    public Map<String, String> getRedirectQueryOpt() {
        return redirectQueryOpt;
    }

    public APIConfig setRedirectQueryOpt(Map<String, String> redirectQueryOpt) {
        this.redirectQueryOpt = redirectQueryOpt;
        return this;
    }

    public Map<String, String> getRedirectAddHeadersOpt() {
        return redirectAddHeadersOpt;
    }

    public APIConfig setRedirectAddHeadersOpt(Map<String, String> redirectAddHeadersOpt) {
        this.redirectAddHeadersOpt = redirectAddHeadersOpt;
        return this;
    }

    public Set<String> getRedirectRemoveHeadersOpt() {
        return redirectRemoveHeadersOpt;
    }

    public APIConfig setRedirectRemoveHeadersOpt(Set<String> redirectRemoveHeadersOpt) {
        this.redirectRemoveHeadersOpt = redirectRemoveHeadersOpt;
        return this;
    }

    public Map<String, String> getRedirectAddCookiesOpt() {
        return redirectAddCookiesOpt;
    }

    public APIConfig setRedirectAddCookiesOpt(Map<String, String> redirectAddCookiesOpt) {
        this.redirectAddCookiesOpt = redirectAddCookiesOpt;
        return this;
    }

    public Set<String> getRedirectRemoveCookiesOpt() {
        return redirectRemoveCookiesOpt;
    }

    public APIConfig setRedirectRemoveCookiesOpt(Set<String> redirectRemoveCookiesOpt) {
        this.redirectRemoveCookiesOpt = redirectRemoveCookiesOpt;
        return this;
    }

    public int getRedirectTimeoutMs() {
        return redirectTimeoutMs;
    }

    public APIConfig setRedirectTimeoutMs(int redirectTimeoutMs) {
        this.redirectTimeoutMs = redirectTimeoutMs;
        return this;
    }

    public List<String> getFilterIds() {
        return filterIds;
    }

    public APIConfig setFilterIds(List<String> filterIds) {
        this.filterIds = filterIds;
        return this;
    }

    public String getMockSrc() {
        return mockSrc;
    }

    public APIConfig setMockSrc(String mockSrc) {
        this.mockSrc = mockSrc;
        return this;
    }
}
