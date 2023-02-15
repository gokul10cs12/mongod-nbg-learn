package com.mongod.learn.mongodnbglearn.model;

import java.util.Objects;

public class FileIdentity {

    private String sha256;
    private String md5;
    private String name;

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileIdentity)) return false;
        FileIdentity that = (FileIdentity) o;
        return Objects.equals(getSha256(), that.getSha256()) && Objects.equals(getMd5(), that.getMd5()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSha256(), getMd5(), getName());
    }
}
