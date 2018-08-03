package com.tairanchina.teval;

import com.tairanchina.teval.inject.InjectFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal invoke
 * @phase prepare-package
 */
public class InjectTEvalInvokeMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException {
        InjectFactory.inject();
    }
}
