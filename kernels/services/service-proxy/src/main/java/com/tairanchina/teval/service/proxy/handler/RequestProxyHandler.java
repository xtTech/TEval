package com.tairanchina.teval.service.proxy.handler;

import com.tairanchina.teval.common.dto.APIConfig;
import com.tairanchina.teval.plugin.template.proxy.filter.FilterContext;
import com.tairanchina.teval.plugin.template.proxy.filter.FilterRequest;
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
    private static final String FLAG_X_TEVAL_APP_ID = "X-Teval-AppId";
    private static final String FLAG_PROXY = "X-Forwarded-For";
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

    private static AntPathMatcher pathMatcher = new AntPathMatcher();

    public Future<FilterContext> handle(HttpServerRequest req) {
        Future<FilterContext> future=Future.future();
        logger.trace("Proxying request: " + req.uri());
        String appId = req.getHeader(FLAG_X_TEVAL_APP_ID);
        if (appId == null) {
            logger.warn("Not Found AppId in Header[" + FLAG_X_TEVAL_APP_ID + "]: " + req.uri());
            future.fail("[404] Not Found AppId in Header[" + FLAG_X_TEVAL_APP_ID + "]: " + req.uri());
            return future;
        }
        String method = req.method().toString();
        String path = req.path();
        final Map<String, String> query = new ConcurrentHashMap<>();
        final Map<String, String> headers = new ConcurrentHashMap<>();
        Optional<APIConfig> hitApiConfig = GlobalContainer.apiConfig.stream()
                .filter(api -> {
                    if (!api.getProxyAppId().equals(appId)
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
        hitApiConfig.ifPresent(api -> {
            String redirectPath = api.getRedirectPath();
            Map<String, String> redirectQuery = api.getRedirectQueryOpt();
            Map<String, String> redirectHeaders = new HashMap<>();
            req.headers().forEach(h -> {
                if (!api.getRedirectRemoveHeadersOpt().contains(h.getKey())) {
                    redirectHeaders.put(h.getKey(), h.getValue());
                }
            });
            redirectHeaders.putAll(api.getRedirectAddHeadersOpt());
            String valueCheck = redirectPath + String.join("", redirectQuery.values()) + String.join("", redirectHeaders.values());
            if (valueCheck.indexOf('{') != -1) {
                Map<String, String> variables = pathMatcher.extractUriTemplateVariables(api.getProxyPath(), path);
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

            FilterContext filterContext = new FilterContext();
            filterContext.setTraceId(UUID.randomUUID().toString())
                    .setCreateTime(System.currentTimeMillis())
                    .setApiConfig(api)
                    .setAppId(appId)
                    .setRequest(new FilterRequest((header -> new String[]{req.getHeader(header)}))
                            .setRemoteIP(req.getHeader(FLAG_PROXY) == null ? req.remoteAddress().host() : req.getHeader(FLAG_PROXY))
                            .setProtocol(api.getRedirectProtocol())
                            .setHost(api.getRedirectHost())
                            .setPort(api.getRedirectPort())
                            .setPath(redirectPath)
                            .setQuery(redirectQuery)
                            .setHeaders(redirectHeaders)
                    );


            Iterator<FilterHandler> filterHandlers = api.getFilterTaskIds().stream()
                    .map(i -> GlobalContainer.filterHandlers.get(i))
                    .collect(Collectors.toList()).iterator();
            future.complete(filterContext);
            while (filterHandlers.hasNext()) {
                FilterHandler handler = filterHandlers.next();
                future.compose(handler::request);
            }
        });
        return future;
    }

    private void parseHeaders(MultiMap headerMutilMap, Map<String, String> headers) {
        headerMutilMap.forEach(i -> headers.put(i.getKey(), i.getValue()));
    }

    private void parseQuery(String queryStr, Map<String, String> query) {
        HttpHelper.parseQuery(queryStr, query);
    }
}
