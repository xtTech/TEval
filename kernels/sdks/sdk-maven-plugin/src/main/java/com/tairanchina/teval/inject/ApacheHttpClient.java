package com.tairanchina.teval.inject;

import javassist.*;

import java.io.IOException;

public class ApacheHttpClient extends ClientInject {

    @Override
    public void doInject(String targetPath) throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("org.apache.http.impl.client.CloseableHttpClient");
        CtClass[] param = new CtClass[4];
        param[0] = classPool.get("org.apache.http.client.methods.HttpUriRequest");
        param[1] = classPool.get("org.apache.http.protocol.HttpContext");
        CtMethod method = ctClass.getDeclaredMethod("execute", param);
        method.setBody("Args.notNull(request, \"HTTP request\");\n" +
                "        java.util.Optional<com.tairanchina.teval.sdk.common.TEvalSDK.ProxyInfo> proxyInfo = com.tairanchina.teval.sdk.common.TEvalSDK.checkAndGetProxyInfo(request.getURI());\n" +
                "        if (proxyInfo.isPresent()) {\n" +
                "            if (request instanceof HttpRequestBase) {\n" +
                "                ((HttpRequestBase) request).setURI(proxyInfo.get().getUri());\n" +
                "            } else {\n" +
                "                throw com.tairanchina.teval.sdk.common.TEvalSDK.unsupport(request);\n" +
                "            }\n" +
                "            proxyInfo.getAdditionHeaders().forEach((k,v)-> request.addHeader(k,v));\n" +
                "        }\n" +
                "        log.trace(\"Invoke URI:\" + request.getURI().toString());\n" +
                "        return doExecute(determineTarget(request), request, context);");
        saveToLocalPath(targetPath, "org.apache.http.protocol.HttpContext", ctClass);
    }
}
