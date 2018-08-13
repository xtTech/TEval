package com.tairanchina.teval;

import com.tairanchina.teval.sdk.TEvalSDK;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

@Mojo(name = "inject", requiresProject = true, defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
@Execute(phase = LifecyclePhase.COMPILE, goal = "inject")
public class InjectTEvalInvokeMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true, required = true)
    private MavenSession session;

    @Component
    private BuildPluginManager pluginManager;

    private static final char FLAG_MAVEN_DEPENDENCY_SPLIT = ':';
    private static final String FLAG_FETCH_DEPENDENCY_PATH = "dependencies";
    private static final char FLAG_AVAILABLE_SPLIT = '#';
    private static final String FLAG_FETCH_AVAILABLE_PATH = "availables";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String targetPath = new File("").getAbsoluteFile() + File.separator + "target" + File.separator;
        try {
            inject(targetPath,
                    fetchAvailableComponents(targetPath).stream()
                            .filter(i -> !i.getArtifactId().equals("sdk-jvm"))
                            .collect(Collectors.toSet()),
                    fetchDependencies(targetPath).stream()
                            .filter(i -> i.getScope().equals("compile"))
                            .collect(Collectors.toList())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<MvnComponent> fetchDependencies(String targetPath) throws MojoExecutionException, IOException {
        executeMojo(
                plugin(
                        groupId("org.apache.maven.plugins"),
                        artifactId("maven-dependency-plugin"),
                        version("3.1.1")
                ),
                goal("list"),
                configuration(
                        element(name("appendOutput"), "true"),
                        element(name("outputFile"), "${project.build.directory}/" + FLAG_FETCH_DEPENDENCY_PATH)
                ),
                executionEnvironment(
                        project,
                        session,
                        pluginManager
                )
        );
        return Files.lines(Paths.get(targetPath + FLAG_FETCH_DEPENDENCY_PATH))
                .skip(2)
                .map(item -> {
                    String[] items = item.trim().split(FLAG_MAVEN_DEPENDENCY_SPLIT + "");
                    if (items.length != 5) {
                        return null;
                    }
                    return new MvnComponent()
                            .setGroupId(items[0])
                            .setArtifactId(items[1])
                            .setVersion(items[3])
                            .setScope(items[4]);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Set<MvnComponent> fetchAvailableComponents(String targetPath) throws IOException {
        JarFile jar = new JarFile(InjectTEvalInvokeMojo.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        java.util.Enumeration enumEntries = jar.entries();
        while (enumEntries.hasMoreElements()) {
            JarEntry file = (JarEntry) enumEntries.nextElement();
            File f = new File(targetPath + FLAG_FETCH_AVAILABLE_PATH + File.separator + file.getName());
            if (file.isDirectory()) {
                f.mkdirs();
                continue;
            }
            InputStream is = jar.getInputStream(file);
            FileOutputStream fos = new java.io.FileOutputStream(f);
            while (is.available() > 0) {
                fos.write(is.read());
            }
            fos.close();
            is.close();
        }
        jar.close();

        return Arrays.stream(Objects.requireNonNull(new File(targetPath + FLAG_FETCH_AVAILABLE_PATH).list()))
                .map(item -> {
                    String[] items = item.trim().split(FLAG_AVAILABLE_SPLIT + "");
                    if (items.length < 2) {
                        return null;
                    }
                    MvnComponent component = new MvnComponent()
                            .setGroupId(items[0])
                            .setArtifactId(items[1]);
                    if (items.length > 2) {
                        component.setVersion(items[2]);
                    }
                    return component;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private void inject(String targetPath, Set<MvnComponent> availableComponents, List<MvnComponent> compileDependencies) throws IOException {
        Optional<MvnComponent> matchedComponent = compileDependencies.stream().filter(dependency ->
                availableComponents.stream()
                        .anyMatch(ac ->
                                ac.getGroupId().equalsIgnoreCase(dependency.getGroupId())
                                        && ac.getArtifactId().equalsIgnoreCase(dependency.getArtifactId())
                                        // TODO 区间版本处理
                                        && ac.getVersion().equalsIgnoreCase(dependency.getVersion()))
        ).findFirst();
        if (matchedComponent.isPresent()) {
            move(new File(targetPath
                            + FLAG_FETCH_AVAILABLE_PATH + File.separator
                            + matchedComponent.get().getGroupId() + FLAG_AVAILABLE_SPLIT + matchedComponent.get().getArtifactId() + FLAG_AVAILABLE_SPLIT + matchedComponent.get().getVersion()),
                    new File(targetPath + "classes")
            );
            move(new File(targetPath
                            + FLAG_FETCH_AVAILABLE_PATH + File.separator
                            + "com.tairanchina.teval#sdk-jvm"),
                    new File(targetPath + "classes")
            );
        } else {
            TEvalSDK.unSupport("your Net Client.");
        }
        new File(targetPath + FLAG_FETCH_AVAILABLE_PATH).delete();
        new File(targetPath + FLAG_FETCH_DEPENDENCY_PATH).delete();

    }

    private void move(File sourcePath, File targetPath) throws IOException {
        if (!targetPath.exists()) {
            targetPath.mkdirs();
        }
        for (File sf : Objects.requireNonNull(sourcePath.listFiles())) {
            if (sf.isDirectory()) {
                move(sf, new File(targetPath.getPath() + File.separator + sf.getName()));
            } else {
                Files.move(sf.toPath(),
                        Paths.get(targetPath.getPath() + File.separator + sf.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

}
