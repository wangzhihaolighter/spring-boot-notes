package com.example.oshi.monitor;

/**
 * cpu信息
 */
public class CpuInfo {
    /**
     * 处理器型号
     */
    private String name;
    /**
     * 物理CPU个数
     */
    private String physicalPackageCount;
    /**
     * 物理核心个数
     */
    private String physicalProcessorCount;
    /**
     * 逻辑CPU个数
     */
    private String logicalProcessorCount;
    /**
     * cpu系统使用率
     */
    private String sysUtilization;
    /**
     * cpu用户使用率
     */
    private String userUtilization;
    /**
     * cpu当前等待率
     */
    private String ioWaitUtilization;
    /**
     * cpu当前使用率
     */
    private String usageUtilization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalPackageCount() {
        return physicalPackageCount;
    }

    public void setPhysicalPackageCount(String physicalPackageCount) {
        this.physicalPackageCount = physicalPackageCount;
    }

    public String getPhysicalProcessorCount() {
        return physicalProcessorCount;
    }

    public void setPhysicalProcessorCount(String physicalProcessorCount) {
        this.physicalProcessorCount = physicalProcessorCount;
    }

    public String getLogicalProcessorCount() {
        return logicalProcessorCount;
    }

    public void setLogicalProcessorCount(String logicalProcessorCount) {
        this.logicalProcessorCount = logicalProcessorCount;
    }

    public String getSysUtilization() {
        return sysUtilization;
    }

    public void setSysUtilization(String sysUtilization) {
        this.sysUtilization = sysUtilization;
    }

    public String getUserUtilization() {
        return userUtilization;
    }

    public void setUserUtilization(String userUtilization) {
        this.userUtilization = userUtilization;
    }

    public String getIoWaitUtilization() {
        return ioWaitUtilization;
    }

    public void setIoWaitUtilization(String ioWaitUtilization) {
        this.ioWaitUtilization = ioWaitUtilization;
    }

    public String getUsageUtilization() {
        return usageUtilization;
    }

    public void setUsageUtilization(String usageUtilization) {
        this.usageUtilization = usageUtilization;
    }
}
