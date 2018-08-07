package com.tairanchina.teval.sdk;

import com.sun.net.httpserver.HttpServer;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TEvalSDkTest {

    private HttpServer adminServer;
    private HttpServer proxyServerOne;
    private HttpServer proxyServerTwo;

    @Before
    public void before() throws IOException {
        // ==== Mock TEval admin server ====
        adminServer = HttpServer.create(new InetSocketAddress(9999), 0);
        adminServer.createContext("/register", httpExchange -> {
            String response = "127.0.0.1:9001,127.0.0.1:9002";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, bs.length);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(bs);
            }
        });
        // ==== Mock TEval Proxy Instance one ====
        proxyServerOne = HttpServer.create(new InetSocketAddress(9001), 0);
        // Mock price query,The normal url template should be "/shop/item/{id}/price"
        proxyServerOne.createContext("/shop/item/1111/price", httpExchange -> {
            System.out.println("Receive a request by instance one.");
            Assert.assertEquals("mock", httpExchange.getRequestHeaders().getFirst("X-TEval-App-Id"));
            // The normal behavior is to forward the request
            String response = "500.00";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, bs.length);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(bs);
            }
        });
        // ==== Mock TEval Proxy Instance two ====
        proxyServerTwo = HttpServer.create(new InetSocketAddress(9002), 0);
        // Mock price query,The normal url template should be "/shop/item/{id}/price"
        proxyServerTwo.createContext("/shop/item/1111/price", httpExchange -> {
            System.out.println("Receive a request by instance two.");
            Assert.assertEquals("mock", httpExchange.getRequestHeaders().getFirst("X-TEval-App-Id"));
            // The normal behavior is to forward the request
            String response = "500.00";
            byte[] bs = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, bs.length);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(bs);
            }
        });
        new Thread(adminServer::start).run();
        new Thread(proxyServerOne::start).run();
        new Thread(proxyServerTwo::start).run();
    }

    @After
    public void after() {
        adminServer.stop(0);
        proxyServerOne.stop(0);
        proxyServerTwo.stop(0);
    }

    @Test
    public void testSDK() throws IOException, URISyntaxException, InterruptedException {
        // ==== Modify Step 1: Add some configs.
        System.setProperty("teval.appid", "mock");
        System.setProperty("teval.appsecret", "ksssdfjwo2r398sn!2498S");
        // Optional
        System.setProperty("teval.url", "http://127.0.0.1:9999");
        // Optional
        System.setProperty("teval.period", "500");

        int loop = 10;
        while (loop-- >= 0) {
            Thread.sleep(100);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            // ==== Modify Step 2: Fetch proxy info before request
            Optional<TEvalSDK.ProxyInfo> proxyInfo = TEvalSDK.checkAndGetProxyInfo(new URI("http://mock.teval.virtual/shop/item/1111/price"));
            // ==== Modify Step 3: Use proxy uri
            HttpGet httpGet = new HttpGet(proxyInfo.get().getUri());
            // ==== Modify Step 4: Add proxy headers
            proxyInfo.get().getAdditionHeaders().forEach(httpGet::setHeader);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String price = EntityUtils.toString(response.getEntity(), "UTF-8");
            Assert.assertEquals("500.00", price);
        }
    }
}
