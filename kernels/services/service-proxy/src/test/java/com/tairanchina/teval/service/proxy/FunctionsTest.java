package com.tairanchina.teval.service.proxy;

import com.tairanchina.teval.service.proxy.function.*;
import com.tairanchina.teval.service.proxy.utils.HttpHelper;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.micrometer.MicrometerMetricsOptions;
import io.vertx.micrometer.VertxPrometheusOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Map;

@RunWith(VertxUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FunctionsTest {

    private static final int CONFIG_SERVER_PORT = 7000;
    private static final int REDIRECT_SERVER_PORT = 6000;
    private static final String PROXY_ID = "testProxy";
    private static final String PROXY_SECRET = "mockSecret";

    private static Vertx vertx;

    private ServerInfo serverInfo = new ServerInfo()
            .setServerUrl("127.0.0.1:" + CONFIG_SERVER_PORT)
            .setProxyId(PROXY_ID)
            .setProxySecret(PROXY_SECRET);

    @BeforeClass
    public static void before(TestContext context) throws IOException {
        vertx = Vertx.vertx(new VertxOptions()
                .setMaxEventLoopExecuteTime(Long.MAX_VALUE)
                .setMetricsOptions(
                        new MicrometerMetricsOptions()
                                .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
                                .setEnabled(true)));
        GlobalContainer.sharedData = vertx.sharedData();
        GlobalContainer.healthChecks = HealthChecks.create(vertx);
        new MockRedisServer();
        // Start Config Server
        vertx.createHttpServer().requestHandler(req -> {
            System.out.println("Request:" + req.path());
            Map<String, String> query = HttpHelper.parseQuery(req.query());
            context.assertEquals(PROXY_ID, query.get("proxyId"));
            context.assertEquals(PROXY_SECRET, query.get("proxySecret"));
            switch (req.path()) {
                case "/proxy/config":
                    req.response().end("{\n" +
                            "    \"http\":{\n" +
                            "        \"proxyPort\":8080,\n" +
                            "        \"managementPort\":9000\n" +
                            "    },\n" +
                            "    \"redis\":{\n" +
                            "        \"host\":\"localhost\",\n" +
                            "        \"port\":6379\n" +
                            "    },\n" +
                            "    \"filters\":{\n" +
                            "        \"mock\":{\n" +
                            "            \"id\":\"mock\"\n" +
                            "        }\n" +
                            "    }\n" +
                            "}");
                    break;
                case "/proxy/apis":
                    req.response().end("[\n" +
                            "    {\n" +
                            "        \"proxyAppId\":\"ucenter\",\n" +
                            "        \"proxyMethod\":\"GET\",\n" +
                            "        \"proxyPath\":\"/user/{userId}\",\n" +
                            "        \"redirectProtocol\":\"http\",\n" +
                            "        \"redirectHost\":\"127.0.0.1\",\n" +
                            "        \"redirectPort\":" + REDIRECT_SERVER_PORT + ",\n" +
                            "        \"redirectPath\":\"/user/{userId}\",\n" +
                            "        \"filterTaskIds\":[\"mock\"]\n" +
                            "    }\n" +
                            "]");
                    break;
                default:
                    req.response().setStatusCode(404).end();
                    break;
            }
        }).listen(CONFIG_SERVER_PORT, ar -> {
            // Start Redirect Server
            vertx.createHttpServer().requestHandler(req -> {
                System.out.println("Request:" + req.path());
                if (req.path().startsWith("/user/")) {
                    req.response().end("OK");
                } else {
                    req.response().setStatusCode(404).end();
                }
            }).listen(REDIRECT_SERVER_PORT, context.asyncAssertSuccess());
        });
    }

    @AfterClass
    public static void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void test01FetchConfig(TestContext context) {
        Async async = context.async();
        FetchConfigFunction.inst().apply(serverInfo, vertx)
                .setHandler(ar -> {
                    context.assertTrue(ar.succeeded());
                    // GlobalContainer.proxyConfig 变量拼装检查
                    context.assertEquals(5, GlobalContainer.proxyConfig.getFilters().get("mock").getMaxFailureTimes());
                    // GlobalContainer.filterHandlers 变量拼装检查
                    context.assertTrue(GlobalContainer.filterHandlers.containsKey("mock"));
                    async.complete();
                });
    }

    @Test
    public void test02SetHttpClient(TestContext context) {
        SetHttpClientFunction.inst().apply(GlobalContainer.proxyConfig.getHttp(), vertx);
    }

    @Test
    public void test03FetchAPIs(TestContext context) {
        Async async = context.async();
        FetchAPIsFunction.inst().apply(serverInfo, vertx).setHandler(ar -> {
            context.assertEquals(1, GlobalContainer.apiConfig.size());
            context.assertEquals("/user/{userId}", GlobalContainer.apiConfig.get(0).getRedirectPath());
            async.complete();
        });
    }

    @Test
    public void test04SetRedisClient(TestContext context) {
        Async async = context.async();
        SetRedisClientFunction.inst().apply(GlobalContainer.proxyConfig.getRedis(), vertx).setHandler(ar -> {
            GlobalContainer.redisClient.echo("ha", ret -> {
                context.assertEquals("ha", ret.result());
                async.complete();
            });
        });
    }

    @Test
    public void test05StartMonitorFunction(TestContext context) {
        Async async = context.async(2);
        StartMonitorFunction.inst().apply(GlobalContainer.proxyConfig.getHttp().getManagementPort(), vertx).setHandler(ar -> {
            GlobalContainer.httpClient.getNow(GlobalContainer.proxyConfig.getHttp().getManagementPort(), "127.0.0.1", "/health", event -> {
                event.bodyHandler(h -> {
                    String healthInfo = h.toString();
                    System.out.println(healthInfo);
                    async.countDown();
                });
            });
            GlobalContainer.httpClient.getNow(GlobalContainer.proxyConfig.getHttp().getManagementPort(), "127.0.0.1", "/metrics", event -> {
                event.bodyHandler(h -> {
                    String metricsInfo = h.toString();
                    System.out.println(metricsInfo);
                    async.countDown();
                });
            });
        });
    }

    @Test
    public void test06StartProxyService(TestContext context) {
        Async async = context.async();
        StartProxyServiceFunction.inst().apply(GlobalContainer.proxyConfig.getHttp(), vertx).setHandler(ar -> {
            HttpClientRequest get= GlobalContainer.httpClient.get(GlobalContainer.proxyConfig.getHttp().getProxyPort(), "127.0.0.1", "/user/11", event -> {
                event.bodyHandler(h -> {
                    context.assertEquals("OK", h.toString());
                    async.complete();
                });
            });
            get.headers().add("X-Teval-AppId","ucenter");
            get.end();
        });
    }


}
