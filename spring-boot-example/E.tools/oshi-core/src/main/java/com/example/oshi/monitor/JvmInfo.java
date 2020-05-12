package com.example.oshi.monitor;

/**
 * jvm信息
 */
public class JvmInfo {
    /**
     * jdk版本
     */
    private String jdkVersion;
    /**
     * jdk home
     */
    private String jdkHome;
    /**
     * jvm内存总量
     */
    private String jvmTotalMemory;
    /**
     * jvm最大可用内存总数
     */
    private String jvmMaxMemory;
    /**
     * jvm已使用内存
     */
    private String jvmUsedMemory;
    /**
     * jvm剩余内存
     */
    private String jvmFreeMemory;
    /**
     * jvm内存使用率
     */
    private String jvmMemoryUtilization;
    /**
     * jvm运行时间
     */
    private String elapsedTime;

    public String getJdkVersion() {
        return jdkVersion;
    }

    public void setJdkVersion(String jdkVersion) {
        this.jdkVersion = jdkVersion;
    }

    public String getJdkHome() {
        return jdkHome;
    }

    public void setJdkHome(String jdkHome) {
        this.jdkHome = jdkHome;
    }

    public String getJvmTotalMemory() {
        return jvmTotalMemory;
    }

    public void setJvmTotalMemory(String jvmTotalMemory) {
        this.jvmTotalMemory = jvmTotalMemory;
    }

    public String getJvmMaxMemory() {
        return jvmMaxMemory;
    }

    public void setJvmMaxMemory(String jvmMaxMemory) {
        this.jvmMaxMemory = jvmMaxMemory;
    }

    public String getJvmUsedMemory() {
        return jvmUsedMemory;
    }

    public void setJvmUsedMemory(String jvmUsedMemory) {
        this.jvmUsedMemory = jvmUsedMemory;
    }

    public String getJvmFreeMemory() {
        return jvmFreeMemory;
    }

    public void setJvmFreeMemory(String jvmFreeMemory) {
        this.jvmFreeMemory = jvmFreeMemory;
    }

    public String getJvmMemoryUtilization() {
        return jvmMemoryUtilization;
    }

    public void setJvmMemoryUtilization(String jvmMemoryUtilization) {
        this.jvmMemoryUtilization = jvmMemoryUtilization;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
