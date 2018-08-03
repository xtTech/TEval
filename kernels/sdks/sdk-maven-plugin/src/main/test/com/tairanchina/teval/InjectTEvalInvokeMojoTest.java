package com.tairanchina.teval;


import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

public class InjectTEvalInvokeMojoTest {

    @Test
    public void testExecute() throws MojoExecutionException {
        new InjectTEvalInvokeMojo().execute();
    }

}