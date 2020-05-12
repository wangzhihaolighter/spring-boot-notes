package com.example.oshi.monitor;

/**
 * 系统信息
 */
public class SysInfo {
    /**
     * 操作系统信息
     */
    private String os;
    /**
     * 系统名称
     */
    private String osName;
    /**
     * 系统架构
     */
    private String osArch;
    /**
     * 本地IP
     */
    private String localIp;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }
}
