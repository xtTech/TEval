package com.tairanchina.teval.service.proxy.handler;

import com.tairanchina.teval.common.dto.APIConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.PluginContext;
import com.tairanchina.teval.plugin.template.proxy.filter.PluginRequest;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import com.tairanchina.teval.service.proxy.utils.HttpHelper;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestProxyHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestProxyHandler.class);
    private static final String FLAG_HEADER_APP_ID = "X-TEval-App-Id";
    private static final String FLAG_HEADER_APP_SECRET = "X-TEval-App-Secret";
    private static final String FLAG_HEADER_SERVICE_ID = "X-TEval-Service-Id";
    private static final String FLAG_PROXY = "X-Forwarded-For";
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

    private static AntPathMatcher pathMatcher = new AntPathMatcher();

    public Future<PluginContext> handle(HttpServerRequest req) {
        Future<PluginContext> future = Future.future();
        logger.trace("Proxying request: " + req.uri());

        // Pre-Check
        String serviceId = req.getHeader(FLAG_HEADER_SERVICE_ID);
        if (serviceId == null) {
            logger.warn("Not Found Service Id in Header[" + FLAG_HEADER_SERVICE_ID + "]: " + req.uri());
            future.fail("[404] Not Found Service Id in Header[" + FLAG_HEADER_SERVICE_ID + "]: " + req.uri());
            return future;
        }
        String appId = req.getHeader(FLAG_HEADER_APP_ID);
        String appSecret = req.getHeader(FLAG_HEADER_APP_SECRET);
        if (appId == null || appSecret == null) {
            logger.warn("Not Found APP Id Or Secret in Header[" + FLAG_HEADER_APP_ID + "," + FLAG_HEADER_APP_SECRET + "]: " + req.uri());
            future.fail("[404] Not Found APP Id Or Secret in Header[" + FLAG_HEADER_APP_ID + "," + FLAG_HEADER_APP_SECRET + "]: " + req.uri());
            return future;
        }
        if (!GlobalContainer.authConfig.containsKey(appId)
                || GlobalContainer.authConfig.get(appId).equals(appSecret)) {
            logger.warn("APP Id Or Secret illegal: " + req.uri());
            future.fail("[401] APP Id Or Secret illegal: " + req.uri());
            return future;
        }

        // Proxy Check
        String method = req.method().toString();
        String path = req.path();
        final Map<String, String> query = new ConcurrentHashMap<>();
        final Map<String, String> headers = new ConcurrentHashMap<>();
        Optional<APIConfig> hitApiConfig = GlobalContainer.apiConfig.stream()
                .filter(api -> {
                    if (!api.getServiceId().equals(serviceId)
                            || !api.getProxyMethod().equals(method)
                            || !pathMatcher.match(api.getProxyPath(), path)) {
                        return false;
                    }
                    if (!api.getProxyQueryOpt().isEmpty()) {
                        parseQuery(req.query(), query);
                        for (Map.Entry<String, Set<String>> entry : api.getProxyQueryOpt().entrySet()) {
                            if (!query.containsKey(entry.getKey())
                                    || entry.getValue() != null && !entry.getValue().contains(query.get(entry.getKey()))) {
                                return false;
                            }
                        }
                    }
                    if (!api.getProxyHeaderOpt().isEmpty()) {
                        parseHeaders(req.headers(), headers);
                        for (Map.Entry<String, Set<String>> entry : api.getProxyHeaderOpt().entrySet()) {
                            if (!headers.containsKey(entry.getKey())
                                    || entry.getValue() != null && !entry.getValue().contains(headers.get(entry.getKey()))) {
                                return false;
                            }
                        }
                    }
                    return true;
                }).findFirst();
        if (!hitApiConfig.isPresent()) {
            logger.warn("Not Found Proxy APIs [" + appId + "] " + req.uri());
            future.fail("[404] Not Found Proxy APIs [" + appId + "] " + req.uri());
            return future;
        }

        // Proxy Process
        APIConfig apiConfig = hitApiConfig.get();
        String redirectPath = apiConfig.getRedirectPath();
        Map<String, String> redirectQuery = apiConfig.getRedirectQueryOpt();
        Map<String, String> redirectHeaders = new HashMap<>();
        req.headers().forEach(h -> {
            if (!apiConfig.getRedirectRemoveHeadersOpt().contains(h.getKey())) {
                redirectHeaders.put(h.getKey(), h.getValue());
            }
        });
        redirectHeaders.putAll(apiConfig.getRedirectAddHeadersOpt());
        // Variable Setting
        String valueCheck = redirectPath + String.join("", redirectQuery.values()) + String.join("", redirectHeaders.values());
        if (valueCheck.indexOf('{') != -1) {
            Map<String, String> variables = pathMatcher.extractUriTemplateVariables(apiConfig.getProxyPath(), path);
            variables.putAll(query);
            variables.putAll(headers);
            Matcher matcher = VARIABLE_PATTERN.matcher(redirectPath);
            while (matcher.find()) {
                redirectPath = matcher.replaceFirst(variables.getOrDefault(matcher.group(1), ""));
                matcher = VARIABLE_PATTERN.matcher(redirectPath);
            }
            for (String key : redirectQuery.keySet()) {
                String value = redirectQuery.get(key);
                if (value.indexOf('{') != -1) {
                    matcher = VARIABLE_PATTERN.matcher(value);
                    while (matcher.find()) {
                        value = matcher.replaceFirst(variables.getOrDefault(matcher.group(1), ""));
                        matcher = VARIABLE_PATTERN.matcher(value);
                    }
                }
                redirectQuery.put(key, value);
            }
            for (String key : redirectHeaders.keySet()) {
                String value = redirectHeaders.get(key);
                if (value.indexOf('{') != -1) {
                    matcher = VARIABLE_PATTERN.matcher(value);
                    while (matcher.find()) {
                        value = matcher.replaceFirst(variables.getOrDefault(matcher.group(1), ""));
                        matcher = VARIABLE_PATTERN.matcher(value);
                    }
                }
                redirectHeaders.put(key, value);
            }
        }
        // TODO cookies

        // Plugin Filters Process
        PluginContext<?> pluginContext = new PluginContext();
        pluginContext.setTraceId(UUID.randomUUID().toString())
                .setCreateTime(System.currentTimeMillis())
                .setServiceId(serviceId)
                .setRequest(new PluginRequest((header -> new String[]{req.getHeader(header)}))
                        .setRemoteIP(req.getHeader(FLAG_PROXY) == null ? req.remoteAddress().host() : req.getHeader(FLAG_PROXY))
                        .setProtocol(apiConfig.getRedirectProtocol())
                        .setHost(apiConfig.getRedirectHost())
                        .setPort(apiConfig.getRedirectPort())
                        .setPath(redirectPath)
                        .setQuery(redirectQuery)
                        .setHeaders(redirectHeaders)
                )
                .set_apiConfig(apiConfig);
        Iterator<PluginHandler> filterHandlers = apiConfig.getPlugins().keySet().stream()
                .map(i -> GlobalContainer.pluginHandlers.get(i))
                .collect(Collectors.toList()).iterator();
        future.complete(pluginContext);
        while (filterHandlers.hasNext()) {
            PluginHandler handler = filterHandlers.next();
            pluginContext.setInnerConfig(apiConfig.getPlugins().get(handler.getPluginCode()));
            future.compose(handler::request);
        }
        return future;
    }

    private void parseHeaders(MultiMap headerMutilMap, Map<String, String> headers) {
        headerMutilMap.forEach(i -> headers.put(i.getKey(), i.getValue()));
    }

    private void parseQuery(String queryStr, Map<String, String> query) {
        HttpHelper.parseQuery(queryStr, query);
    }
}
