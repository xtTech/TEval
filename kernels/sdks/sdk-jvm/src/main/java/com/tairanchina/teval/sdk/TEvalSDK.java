package com.tairanchina.teval.sdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TEvalSDK {

    private static final String USER_AGENT = "TEvalSDK";
    private static final String FLAG_HEADER_APP_ID = "X-TEval-App-Id";
    private static final String FLAG_HEADER_APP_SECRET = "X-TEval-App-Secret";
    private static final String FLAG_HEADER_SERVICE_ID = "X-TEval-Service-Id";
    private static final String PROXY_HOST = "teval.virtual";

    private static ConfigProcessor.Config config;

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void init() throws IOException {
        config = ConfigProcessor.fetchConfig();
        fetchProxyAddresses();
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(
                TEvalSDK::fetchProxyAddresses,
                config.getFetchPeriodMs(),
                config.getFetchPeriodMs(),
                TimeUnit.MILLISECONDS);
    }

    private static void fetchProxyAddresses() {
        try {
            HttpHelper.Response response = HttpHelper.get(config.getServiceUrl() + "/register", new HashMap<String, String>() {{
                put(FLAG_HEADER_APP_ID, config.getAppId());
                put(FLAG_HEADER_APP_SECRET, config.getAppSecret());
            }});
            if (response.getStatusCode() == 200) {
                config.setProxyAddresses(response.getBody().trim().split(","));
            } else if (response.getStatusCode() == 401) {
                throw new SecurityException("Illegal AppId OR AppSecret");
            } else {
                throw new UnknownServiceException("Visit TEval Service Error: " + response.getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显式禁用TEval功能
     */
    public static void disabled() {
        config.setEnabled(false);
    }

    /**
     * 代理是否命中检查
     * <p>
     * 仅当请求host为 <serviceId>.teval.virtual 时才代理
     */
    public static Optional<ProxyInfo> checkAndGetProxyInfo(URI uri) throws IOException {
        if (!config.isEnabled()) {
            return Optional.empty();
        }
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
     * 仅当请求host为 <serviceId>.teval.virtual 时才代理
     */
    public static Optional<ProxyInfo> checkAndGetProxyInfo(String host) throws IOException {
        if (!config.isEnabled()) {
            return Optional.empty();
        }
        if (host.endsWith(PROXY_HOST)) {
            return Optional.of(getProxyInfo(host));
        }
        return Optional.empty();
    }

    public static ProxyInfo getProxyInfo(String host) throws IOException {
        if (config.getProxyAddresses() == null || config.getProxyAddresses().length == 0) {
            throw new UnknownHostException("Teval proxy address is empty.");
        }
        String serviceId = host.substring(0, host.indexOf('.'));
        String[] addr = config.getProxyAddresses()[(int) (Math.random() * config.getProxyAddresses().length)].split(":");
        return new ProxyInfo(addr[0], Integer.valueOf(addr[1]), new HashMap<String, String>() {{
            put(FLAG_HEADER_APP_ID, config.getAppId());
            put(FLAG_HEADER_APP_SECRET, config.getAppSecret());
            put(FLAG_HEADER_SERVICE_ID, serviceId);
        }});
    }

    public static void unSupport(Class<?> clz) {
        throw new UnSupportException(clz.getName());
    }

    public static void unSupport(String message) {
        throw new UnSupportException(message);
    }

    public static class ProxyInfo {

        private String host;
        private int port;
        private URI uri;
        private Map<String, String> additionHeaders;

        ProxyInfo(String host, int port, Map<String, String> additionHeaders) {
            this.host = host;
            this.port = port;
            this.additionHeaders = additionHeaders;
        }

        String getHost() {
            return host;
        }

        int getPort() {
            return port;
        }

        /**
         * 此字段仅在调用 {@link #checkAndGetProxyInfo(URI)} 有输出
         */
        public URI getUri() {
            return uri;
        }

        void setUri(URI uri) {
            this.uri = uri;
        }

        public Map<String, String> getAdditionHeaders() {
            return additionHeaders;
        }

        public ProxyInfo setAdditionHeaders(Map<String, String> additionHeaders) {
            this.additionHeaders = additionHeaders;
            return this;
        }
    }

    public static class UnSupportException extends RuntimeException {

        public UnSupportException(String type) {
            super("Teval do NOT support [" + type + "]");
        }
    }

    public static class ConfigProcessor {

        private static final String DEFAULT_CONFIG_PATH = "./config/teval.properties";

        private static final String FLAG_CONF_ENABLED = "teval.enabled";
        private static final String FLAG_CONF_CONFIG_PATH = "teval.path";
        private static final String FLAG_CONF_SEREVICE_URL = "teval.url";
        private static final String FLAG_CONF_SEREVICE_CONNECT_TIMEOUT = "teval.connect.timeout";
        private static final String FLAG_CONF_SEREVICE_READ_TIMEOUT = "teval.read.timeout";
        private static final String FLAG_CONF_APP_ID = "teval.appid";
        private static final String FLAG_CONF_APP_SECRET = "teval.appsecret";
        private static final String FLAG_CONF_FETCH_PERIOD_MS = "teval.period";

        static Config fetchConfig() throws IOException {
            Config config = new Config();
            Properties properties = System.getProperties();
            // 先从配置文件加载
            String configPath = properties.contains(FLAG_CONF_CONFIG_PATH) ? properties.getProperty(FLAG_CONF_CONFIG_PATH) : DEFAULT_CONFIG_PATH;
            File configFile = new File(configPath);
            if (configFile.exists()) {
                Files.lines(configFile.toPath()).forEach(item -> {
                    String[] items = item.trim().split("=");
                    if (items.length == 2) {
                        String value = items[1].trim();
                        switch (items[0].trim()) {
                            case FLAG_CONF_ENABLED:
                                config.setEnabled(Boolean.valueOf(value));
                                break;
                            case FLAG_CONF_SEREVICE_URL:
                                config.setServiceUrl(value);
                                break;
                            case FLAG_CONF_SEREVICE_CONNECT_TIMEOUT:
                                config.setServiceConnectTimeout(Integer.valueOf(value));
                                break;
                            case FLAG_CONF_SEREVICE_READ_TIMEOUT:
                                config.setServiceReadTimeout(Integer.valueOf(value));
                                break;
                            case FLAG_CONF_APP_ID:
                                config.setAppId(value);
                                break;
                            case FLAG_CONF_APP_SECRET:
                                config.setAppSecret(value);
                                break;
                            case FLAG_CONF_FETCH_PERIOD_MS:
                                config.setFetchPeriodMs(Integer.valueOf(value));
                                break;
                        }
                    }
                });
            }
            // 允许命令参数覆写
            config.setEnabled(Boolean.valueOf((String) properties.getOrDefault(FLAG_CONF_ENABLED, Boolean.toString(config.isEnabled()))));
            config.setServiceUrl((String) properties.getOrDefault(FLAG_CONF_SEREVICE_URL, config.getServiceUrl()));
            config.setServiceConnectTimeout(Integer.valueOf((String) properties.getOrDefault(FLAG_CONF_SEREVICE_CONNECT_TIMEOUT, config.getServiceConnectTimeout() + "")));
            config.setServiceReadTimeout(Integer.valueOf((String) properties.getOrDefault(FLAG_CONF_SEREVICE_READ_TIMEOUT, config.getServiceReadTimeout() + "")));
            config.setAppId((String) properties.getOrDefault(FLAG_CONF_APP_ID, config.getAppId()));
            config.setAppSecret((String) properties.getOrDefault(FLAG_CONF_APP_SECRET, config.getAppSecret()));
            config.setFetchPeriodMs(Integer.valueOf((String) properties.getOrDefault(FLAG_CONF_FETCH_PERIOD_MS, config.getFetchPeriodMs() + "")));

            if (config.getAppId() == null
                    || config.getAppId().isEmpty()
                    || config.getAppSecret() == null
                    || config.getAppSecret().isEmpty()) {
                throw new RuntimeException("You must specify " + FLAG_CONF_APP_ID + " and " + FLAG_CONF_APP_SECRET + " in the configuration file or command parameters");
            }
            return config;
        }

        static class Config {

            private static final String DEFAULT_SERVICE_URL = "http://teval.tairanchina.com/admin";
            private static final int DEFAULT_SERVICE_CONNECT_TIMEOUT_MS = 5000;
            private static final int DEFAULT_SERVICE_READ_TIMEOUT_MS = 5000;
            private static final int DEFAULT_FETCH_PERIOD_MS = 60000;

            private boolean enabled = true;
            private int fetchPeriodMs = DEFAULT_FETCH_PERIOD_MS;
            private String serviceUrl = DEFAULT_SERVICE_URL;
            private int serviceConnectTimeout = DEFAULT_SERVICE_CONNECT_TIMEOUT_MS;
            private int serviceReadTimeout = DEFAULT_SERVICE_READ_TIMEOUT_MS;
            private String appId;
            private String appSecret;
            // host:port,...
            private String[] proxyAddresses;

            boolean isEnabled() {
                return enabled;
            }

            void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            int getFetchPeriodMs() {
                return fetchPeriodMs;
            }

            void setFetchPeriodMs(int fetchPeriodMs) {
                this.fetchPeriodMs = fetchPeriodMs;
            }

            String getServiceUrl() {
                return serviceUrl;
            }

            void setServiceUrl(String serviceUrl) {
                this.serviceUrl = serviceUrl;
            }

            int getServiceConnectTimeout() {
                return serviceConnectTimeout;
            }

            void setServiceConnectTimeout(int serviceConnectTimeout) {
                this.serviceConnectTimeout = serviceConnectTimeout;
            }

            int getServiceReadTimeout() {
                return serviceReadTimeout;
            }

            void setServiceReadTimeout(int serviceReadTimeout) {
                this.serviceReadTimeout = serviceReadTimeout;
            }

            String getAppId() {
                return appId;
            }

            void setAppId(String appId) {
                this.appId = appId;
            }

            String getAppSecret() {
                return appSecret;
            }

            void setAppSecret(String appSecret) {
                this.appSecret = appSecret;
            }

            String[] getProxyAddresses() {
                return proxyAddresses;
            }

            void setProxyAddresses(String[] proxyAddresses) {
                this.proxyAddresses = proxyAddresses;
            }
        }
    }

    public static class HttpHelper {

        static Response get(String url, Map<String, String> header) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            header.forEach(connection::setRequestProperty);
            connection.setConnectTimeout(config.getServiceConnectTimeout());
            connection.setReadTimeout(config.getServiceReadTimeout());
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return new Response().setStatusCode(responseCode);
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return new Response().setStatusCode(200).setBody(response.toString());
        }

        static class Response {

            private int statusCode;
            private String body;

            int getStatusCode() {
                return statusCode;
            }

            Response setStatusCode(int statusCode) {
                this.statusCode = statusCode;
                return this;
            }

            String getBody() {
                return body;
            }

            Response setBody(String body) {
                this.body = body;
                return this;
            }
        }
    }
}
