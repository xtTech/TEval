package com.tairanchina.teval.example.loan;

import com.ecfront.dew.common.$;
import com.tairanchina.teval.sdk.TEvalSDK;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @GetMapping(value = "/{userId}/credit")
    public long getCreditAmount(@PathVariable("userId") String userId) throws IOException {
       /* // =========== 原始方法 ===========
        TEvalSDK.ProxyInfo proxyInfo = TEvalSDK.getProxyInfo("sl.teval.virtual");
        if (Boolean.valueOf($.http.get("http://" + proxyInfo.getHost() + ":" + proxyInfo.getPort() + "/bl/" + userId, proxyInfo.getAdditionHeaders()))) {
            return 0L;
        }
        return 10000L;*/

        // =========== 劫持方法 ===========
        if (Boolean.valueOf($.http.get("http://sl.teval.virtual/bl/" + userId))) {
            return 0L;
        }
        return 10000L;
    }

}
