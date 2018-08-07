package com.tairanchina.teval;

import org.apache.maven.artifact.versioning.ComparableVersion;

public class MvnComponent {

    private String groupId;
    private String artifactId;
    private ComparableVersion version;
    private String scope;

    public boolean ge(String targetVersion) {
        return version.compareTo(new ComparableVersion(targetVersion)) >= 0;
    }

    public boolean gt(String targetVersion) {
        return version.compareTo(new ComparableVersion(targetVersion)) > 0;
    }

    public boolean le(String targetVersion) {
        return version.compareTo(new ComparableVersion(targetVersion)) <= 0;
    }

    public boolean lt(String targetVersion) {
        return version.compareTo(new ComparableVersion(targetVersion)) < 0;
    }

    public boolean eq(String targetVersion) {
        return version.compareTo(new ComparableVersion(targetVersion)) == 0;
    }

    public String getGroupId() {
        return groupId;
    }

    public MvnComponent setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public MvnComponent setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version.toString();
    }

    public MvnComponent setVersion(String version) {
        this.version = new ComparableVersion(version);
        return this;
    }

    public String getScope() {
        return scope;
    }

    public MvnComponent setScope(String scope) {
        this.scope = scope;
        return this;
    }

}
