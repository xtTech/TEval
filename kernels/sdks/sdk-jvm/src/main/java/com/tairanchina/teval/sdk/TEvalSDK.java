package com.tairanchina.teval.sdk;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TEvalSDK {

    private static final String FLAG_HEADER_ACCOUNT_SECRET = "X-TEval-Account-Secret";
    private static final String FLAG_HEADER_APP_CODE = "X-TEval-App-Code";
    private static final String PROXY_HOST = "teval.virtual";

    private static String TEVAL_ADMIN_SERVICE_URL = "http://teval.tairanchina.com/admin";

    private static String accountSecret;
    private static String[] proxyAddresses;

    public static void enabled() throws IOException {
        // TODO
    }

    /**
     * 代理是否命中检查
     * <p>
     * 仅当请求host为 <appId>.teval.virtual 时才代理
     */
    public static Optional<ProxyInfo> checkAndGetProxyInfo(URI uri) throws IOException {
        if (uri.getHost().endsWith(PROXY_HOST)) {
            ProxyInfo proxyInfo = getProxyInfo(uri.getHost());
            try {
                URI proxyUri = new URI("HTTP", null, proxyInfo.getHost(), proxyInfo.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment());
                proxyInfo.setUri(proxyUri);
                return Optional.of(proxyInfo);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * 代理是否命中检查
     * <p>
     * 仅当请求host为 <appId>.teval.virtual 时才代理
     */
    public static Optional<ProxyInfo> checkAndGetProxyInfo(String host) throws IOException {
        if (host.endsWith(PROXY_HOST)) {
            return Optional.of(getProxyInfo(host));
        }
        return Optional.empty();
    }

    public static ProxyInfo getProxyInfo(String host) throws IOException {
        if (proxyAddresses == null || proxyAddresses.length == 0) {
            throw new UnknownHostException("Teval proxy address is empty.");
        }
        String appCode = host.substring(0, host.indexOf('.'));
        String[] addr = proxyAddresses[(int) (Math.random() * proxyAddresses.length)].split(":");
        return new ProxyInfo(addr[0], Integer.valueOf(addr[1]), new HashMap<String, String>() {{
            put(FLAG_HEADER_ACCOUNT_SECRET, accountSecret);
            put(FLAG_HEADER_APP_CODE, appCode);
        }});
    }

    public static class ProxyInfo {

        private String host;
        private int port;
        private URI uri;
        private Map<String, String> additionHeaders;

        public ProxyInfo(String host, int port, Map<String, String> additionHeaders) {
            this.host = host;
            this.port = port;
            this.additionHeaders = additionHeaders;
        }

        public String getHost() {
            return host;
        }

        public ProxyInfo setHost(String host) {
            this.host = host;
            return this;
        }

        public int getPort() {
            return port;
        }

        public ProxyInfo setPort(int port) {
            this.port = port;
            return this;
        }

        public URI getUri() {
            return uri;
        }

        public ProxyInfo setUri(URI uri) {
            this.uri = uri;
            return this;
        }

        public Map<String, String> getAdditionHeaders() {
            return additionHeaders;
        }

        public ProxyInfo setAdditionHeaders(Map<String, String> additionHeaders) {
            this.additionHeaders = additionHeaders;
            return this;
        }
    }
}
