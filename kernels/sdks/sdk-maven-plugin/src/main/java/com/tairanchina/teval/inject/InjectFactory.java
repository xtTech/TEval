package com.tairanchina.teval.inject;

public class InjectFactory {

    public static void inject() {
        String targetPath=InjectFactory.class.getResource("/").getPath();
        new ApacheHttpClient().inject(targetPath);
    }
}
