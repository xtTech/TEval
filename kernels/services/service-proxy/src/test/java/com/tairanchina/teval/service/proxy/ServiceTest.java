package com.tairanchina.teval.service.proxy;

import com.tairanchina.teval.service.proxy.function.FetchConfigFunction;
import com.tairanchina.teval.service.proxy.utils.HttpHelper;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(VertxUnitRunner.class)
public class ServiceTest {

    private Vertx vertx;

    @Before
    public void before(TestContext context) {
        vertx = Vertx.vertx();
        vertx.createHttpServer().requestHandler(req -> {
            Map<String, String> query = HttpHelper.parseQuery(req.query());
            context.assertEquals("testProxy", query.get("proxyId"));
            context.assertEquals("mockSecret", query.get("proxySecret"));
            if (req.path().equals("/proxy/config")) {
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
                        "        \"Mock\":{\n" +
                        "            \"taskId\":\"Mock\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
            }
        }).
                listen(7000, ar -> {
                    DeploymentOptions options = new DeploymentOptions()
                            .setConfig(new JsonObject()
                                    .put("serverUrl", "127.0.0.1:7000")
                                    .put("proxyId", "testProxy")
                                    .put("proxySecret", "mockSecret")
                            );
                    vertx.deployVerticle(ProxyApplication.class.getName(), options, context.asyncAssertSuccess());
                });
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testFetchConfig(TestContext context) throws InterruptedException {
    }

}
