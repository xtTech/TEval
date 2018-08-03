package com.tairanchina.teval.inject;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;

public abstract class ClientInject {

    public void inject(String targetPath) {
        try {
            doInject(targetPath);
        } catch (NotFoundException e) {
            // 不存在对应的包，忽略
        } catch (IOException | CannotCompileException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected void saveToLocalPath(String targetPath, String packageName, CtClass ctClass) throws CannotCompileException, IOException {
        ctClass.writeFile(targetPath + packageName.replaceAll("\\.", File.separatorChar + "") + ctClass.getSimpleName());
    }

    abstract protected void doInject(String targetPath) throws NotFoundException, CannotCompileException, IOException;

}
