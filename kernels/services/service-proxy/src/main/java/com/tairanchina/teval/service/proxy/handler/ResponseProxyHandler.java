package com.tairanchina.teval.service.proxy.handler;

import com.tairanchina.teval.plugin.template.proxy.filter.FilterContext;
import com.tairanchina.teval.service.proxy.GlobalContainer;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.stream.Collectors;

public class ResponseProxyHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseProxyHandler.class);

    public Future<FilterContext> handle(FilterContext context) {
        Future<FilterContext> future = Future.future();
        Iterator<FilterHandler> filterHandlers = context.getApiConfig().getFilterIds().stream()
                .map(i -> GlobalContainer.filterHandlers.get(i))
                .collect(Collectors.toList()).iterator();
        future.complete(context);
        while (filterHandlers.hasNext()) {
            FilterHandler handler = filterHandlers.next();
            future.compose(handler::response);
        }
        return future;
    }

}
